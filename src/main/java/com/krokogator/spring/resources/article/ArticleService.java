package com.krokogator.spring.resources.article;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.article.dto.PatchArticleDTO;
import com.krokogator.spring.resources.article.dto.PostArticleDTO;
import com.krokogator.spring.resources.category.Category;
import com.krokogator.spring.resources.category.CategoryRepository;
import com.krokogator.spring.resources.user.CurrentUser;
import com.krokogator.spring.resources.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    public void deleteArticle(Long id) throws ClientErrorException {
        Article article = getArticle(id);
        articleRepository.delete(article);
    }

    public Article likeArticle(Article article) {
        Predicate<User> matcher = p -> p.getId().equals(CurrentUser.getId());

        if(article.getLikes().stream().anyMatch(matcher)) {
            //Article already liked by this user, do nothing.
            return article;
        }

        article.getLikes().add(new User(CurrentUser.getId()));
        return articleRepository.save(article);
    }

    public Article dislikeArticle(Article article) {
        Predicate<User> matcher = p -> p.getId().equals(CurrentUser.getId());

        if(article.getLikes().stream().noneMatch(matcher)){
            //Article already disliked by this user, do nothing.
            return article;
        }

        article.getLikes().removeIf(x -> x.getId().equals(CurrentUser.getId()));
        return articleRepository.save(article);
    }

    public List<Article> getArticles(Long authorId, String categoryName, Integer page, Integer pageSize){
        //Default page/page size values if null
        page = (page == null) ? 0 : page;
        pageSize = (pageSize == null) ? 1000 : pageSize;

        //Page request instance
        Pageable pageable = PageRequest.of(page, pageSize);

        //findAllBy not null parameters
        if(authorId == null && categoryName == null) return articleRepository.findAllByOrderByCreatedDesc(pageable).getContent();
        else if (categoryName == null) return articleRepository.findAllByUserIdOrderByCreatedDesc(authorId, pageable).getContent();
        else if (authorId == null) return articleRepository.findAllByCategoryNameIgnoreCaseOrderByCreatedDesc(categoryName, pageable).getContent();
        else return articleRepository.findAllByUserIdAndCategoryNameIgnoreCaseOrderByCreatedDesc(authorId, categoryName, pageable).getContent();
    }

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
        //Update liked if provided
        if(dto.liked != null) {
            article = (dto.liked)? likeArticle(article) : dislikeArticle(article);
        }

        return articleRepository.save(article);
    }
}
