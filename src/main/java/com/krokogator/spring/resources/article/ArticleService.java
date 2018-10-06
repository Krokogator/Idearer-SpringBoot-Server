package com.krokogator.spring.resources.article;

import com.krokogator.spring.resources.category.Category;
import com.krokogator.spring.resources.user.CurrentUser;
import com.krokogator.spring.resources.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.List;


@Service
@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    // @PostAuthorize("hasRole('ADMIN') OR @loggedInUser.getId() == #article.author.id ")
    public Article addArticle(Article article) {
        article.setUser(new User(CurrentUser.getId()));
        //Article articleDB = new Article(article.getTitle(), article.getContent());
        //articleDB.setUser(new User(userId));
        //articleDB.setCategory(new Category(categoryId));
        return articleRepository.save(article);
    }

    public Article getArticle(Long id) {
        return articleRepository.getById(id);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public void likeArticle(Long id) {
        Article article = articleRepository.getById(id);
        article.getLikes().add(new User(CurrentUser.getId()));
        articleRepository.save(article);
    }

    public void dislikeArticle(Long id) {
        Article article = articleRepository.getById(id);
        article.getLikes().removeIf(x -> x.getId().equals(CurrentUser.getId()));
        articleRepository.save(article);
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

    public Article updateArticle(Article article, Long articleId) {
        Article articleDB = articleRepository.getById(articleId);
        if(article.getTitle() != null) articleDB.setTitle(article.getTitle());
        if(article.getContent() != null) articleDB.setContent(article.getContent());
        if(article.getCategory() != null) articleDB.setCategory(new Category(article.getCategory().getId()));
        return articleRepository.save(articleDB);
    }
}
