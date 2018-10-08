package com.krokogator.spring.resources.article;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.article.dto.PostArticleDTO;
import com.krokogator.spring.resources.category.Category;
import com.krokogator.spring.resources.category.CategoryRepository;
import com.krokogator.spring.resources.user.CurrentUser;
import com.krokogator.spring.resources.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.List;


@Service
@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Article addArticle(Article article) throws ClientErrorException {
        //Set author as currently logged in user
        article.setUser(new User(CurrentUser.getId()));

        //Check if category exists
        categoryRepository.findById(article.getCategory().getId())
                .orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Category '"+article.getCategory().getId()+"' not found."));

        return articleRepository.save(article);
    }

    public Article getArticle(Long id) throws ClientErrorException {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Article '"+id+"' not found."));
    }

    public void deleteArticle(Long id) throws ClientErrorException {
        Article article = getArticle(id);
        articleRepository.delete(article);
    }

    public Article likeArticle(Article article) {
        article.getLikes().add(new User(CurrentUser.getId()));
        return articleRepository.save(article);
    }

    public Article dislikeArticle(Article article) {
        article.getLikes().removeIf(x -> x.getId().equals(CurrentUser.getId()));
        return articleRepository.save(article);
    }

    public List<Article> getArticles(Long authorId, String categoryName, Integer page, Integer pageSize){

        Pageable pageable = PageRequest.of(page, pageSize);

        if(authorId == null && categoryName == null){
            return articleRepository.findAllByOrderByCreatedDesc(pageable).getContent();
        } else if (categoryName == null){
            return articleRepository.findAllByUserIdOrderByCreatedDesc(authorId, pageable).getContent();
        } else if (authorId == null){
            return articleRepository.findAllByCategoryNameIgnoreCaseOrderByCreatedDesc(categoryName, pageable).getContent();
        }

        return articleRepository.findAllByUserIdAndCategoryNameIgnoreCaseOrderByCreatedDesc(authorId, categoryName, pageable).getContent();
    }

    public Article updateArticle(PostArticleDTO dto, Long articleId) throws ClientErrorException {
        //Check if article exists
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() ->  new ClientErrorException(HttpStatus.NOT_FOUND, "Article '"+articleId+"' not found."));
        //Update title if provided
        if(dto.title != null) article.setTitle(dto.title);
        //Update content if provided
        if(dto.content != null) article.setContent(dto.content);
        //Update liked if provided
        if(dto.liked != null) {
            if (dto.liked) article = likeArticle(article);
            else article = dislikeArticle(article);
        }

        return articleRepository.save(article);
    }
}
