package com.krokogator.spring.resources.article;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.article.dto.PatchArticleDTO;
import com.krokogator.spring.resources.article.dto.PostArticleDTO;
import com.krokogator.spring.resources.category.Category;
import com.krokogator.spring.resources.category.CategoryRepository;
import com.krokogator.spring.resources.user.CurrentUser;
import com.krokogator.spring.resources.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Predicate;


@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Article addArticle(PostArticleDTO dto) throws ClientErrorException {
        Article article = new Article(
                dto.title,
                dto.content,
                new User(CurrentUser.getId()),
                new Category(dto.getCategory().getId())
        );

        //Check if category exists
        categoryRepository.findById(dto.getCategory().getId())
                .orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Category '"+article.getCategory().getId()+"' not found."));

        return articleRepository.save(article);
    }

    public Article getArticle(Long id) throws ClientErrorException {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Article '"+id+"' not found."));
        return article;
    }

    @PostAuthorize("returnObject.user.id == @CurrentUser.id OR hasRole('ADMIN')")
    public Article deleteArticle(Long id) throws ClientErrorException {
        Article article = getArticle(id);
        articleRepository.delete(article);
        return article;
    }

    public Article likeArticle(Article article) throws ClientErrorException {
        Predicate<User> matcher = p -> p.getId().equals(CurrentUser.getId());

        if(article.getLikes().stream().anyMatch(matcher)) {
            throw new ClientErrorException(HttpStatus.CONFLICT, "Article " + article.getId() + " already liked.");
        }

        article.getLikes().add(new User(CurrentUser.getId()));
        return article;
    }

    public Article dislikeArticle(Article article) throws ClientErrorException {
        Predicate<User> matcher = p -> p.getId().equals(CurrentUser.getId());

        if(article.getLikes().stream().noneMatch(matcher)){
            throw new ClientErrorException(HttpStatus.CONFLICT, "Article " + article.getId() + " already disliked.");
        }

        article.getLikes().removeIf(x -> x.getId().equals(CurrentUser.getId()));
        return articleRepository.save(article);
    }

    public Page<Article> getArticles(Long userId, String categoryName, ArticleStatus status, Integer pageIndex, Integer pageSize, ArticleSort sort) {
        //Default page/page size values if null
        pageIndex = (pageIndex == null) ? 1 : pageIndex;
        pageIndex--;
        pageSize = (pageSize == null) ? 1000 : pageSize;

        //Page request instance
        Pageable page = PageRequest.of(pageIndex, pageSize);

        return articleRepository.getArticlesByAdvancedQuery(userId, categoryName, status, page, sort);
        //return articleRepository.findAll(page);

    }

    @PostAuthorize("( returnObject.user.id == @CurrentUser.id AND returnObject.status.toString() == 'REJECTED' ) OR hasRole('ADMIN')")
    public Article updateArticle(PatchArticleDTO dto, Long articleId) throws ClientErrorException {
        //Check if article exists
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() ->  new ClientErrorException(HttpStatus.NOT_FOUND, "Article '"+articleId+"' not found."));
        //Update title if provided
        if(dto.title != null) article.setTitle(dto.title);
        //Update content if provided
        if(dto.content != null) article.setContent(dto.content);
        //Update category if provided
        if(dto.getCategory() != null) article.setCategory(new Category(dto.getCategory().getId()));
//        //Update liked if provided
//        if(dto.liked != null) {
//            article = (dto.liked)? likeArticle(article) : dislikeArticle(article);
//        }

        return updateArticle(article);
    }

    @PreAuthorize("hasRole('USER')")
    public Article likeOrDislikeArticle(PatchArticleDTO dto, Long articleId) throws ClientErrorException {
        //Check if article exists
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Article '" + articleId + "' not found."));

        //Update liked if provided
        if (dto.liked != null) {
            article = (dto.liked) ? likeArticle(article) : dislikeArticle(article);
        }

        return updateArticle(article);
    }

    private Article updateArticle(Article article) {
        return articleRepository.save(article);
    }

    @PostAuthorize("hasRole('ADMIN') OR returnObject.user.id == @CurrentUser.id")
    public Article updateStatus(ArticleStatus status, Long articleId, HttpServletRequest request) throws ClientErrorException {
        //Check if article exists
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Article '" + articleId + "' not found."));

        //Check if user updates article status from REJECTED to PENDING (if not, throw FORBIDDEN 403)
        if ((status != ArticleStatus.PENDING || article.getStatus() != ArticleStatus.REJECTED) && request.isUserInRole("USER")) {
            throw new ClientErrorException(HttpStatus.FORBIDDEN, "User can only modify own articles status from " + ArticleStatus.REJECTED.toString() + "to " + ArticleStatus.PENDING.toString());
        }

        //Update status if provided
        if (status != null) {
            article.setStatus(status);
        }

        return updateArticle(article);
    }

    public List<Article> findReported() {
        return articleRepository.findAllByReportsNotEmpty();
    }
}
