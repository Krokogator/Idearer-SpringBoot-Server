package com.krokogator.spring.resources.article;

import com.krokogator.spring.resources.article.projection.RequestBodyArticle;
import com.krokogator.spring.resources.category.Category;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "Articles")
@RequestMapping(value = "/articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping
    public Article addArticle(@RequestBody RequestBodyArticle article){

        Article articleDB = new Article();
        articleDB.setTitle(article.title);
        articleDB.setContent(article.content);
        articleDB.setCategory(new Category(article.category.id));

        return articleService.addArticle(articleDB);
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable Long id){
        return articleService.getArticle(id);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id){
        articleService.deleteArticle(id);
    }

    @PatchMapping("/{id}/like")
    public void likeArticle(@PathVariable Long id){
        articleService.likeArticle(id);
    }

    @PatchMapping("/{id}/dislike")
    public void dislikeArticle(@PathVariable Long id) { articleService.dislikeArticle(id); }

    @GetMapping
    public List<Article> getArticles(
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, name = "UNIMPLEMENTED sort") String sort,
            @RequestParam(required = false, name = "UNIMPLEMENTED verified") Boolean verified){
        page = (page == null) ? 0 : page;
        pageSize = (pageSize == null) ? 1000 : pageSize;
        categoryName = (categoryName == null) ? null : categoryName.toLowerCase();
        return articleService.getArticles(authorId, categoryName, page, pageSize);
    }

    @PatchMapping("/{id}")
    public Article updateArticle(@RequestBody RequestBodyArticle article, @PathVariable Long id){
        Article articleDB = new Article();
        articleDB.setTitle(article.title);
        articleDB.setContent(article.content);
        if(article.category != null){
            articleDB.setCategory(new Category(article.category.id));
        }


        return articleService.updateArticle(articleDB, id);
    }

}
