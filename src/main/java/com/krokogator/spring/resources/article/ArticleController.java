package com.krokogator.spring.resources.article;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.article.dto.PatchArticleDTO;
import com.krokogator.spring.resources.article.dto.PostArticleDTO;
import com.krokogator.spring.resources.category.Category;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "Articles")
@RequestMapping(value = "/articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public Article addArticle(@RequestBody @Validated PostArticleDTO dto) throws ClientErrorException {
        return articleService.addArticle(dto);
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable Long id) throws ClientErrorException {
        return articleService.getArticle(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public void deleteArticle(@PathVariable Long id) throws ClientErrorException {
        articleService.deleteArticle(id);
    }

    @GetMapping
    public List<Article> getArticles(
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false, name = "UNIMPLEMENTED sort") String sort,
            @RequestParam(required = false, name = "UNIMPLEMENTED verified") Boolean verified){
        return articleService.getArticles(authorId, categoryName, page, pageSize);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public Article updateArticle(@RequestBody @Validated PatchArticleDTO articleDTO, @PathVariable Long id) throws ClientErrorException {
        return articleService.updateArticle(articleDTO, id);
    }

}
