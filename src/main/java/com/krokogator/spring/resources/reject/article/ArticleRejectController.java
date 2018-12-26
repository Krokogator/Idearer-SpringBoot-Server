package com.krokogator.spring.resources.reject.article;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.reject.article.dto.PostArticleRejectDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags = "Rejects")
public class ArticleRejectController {

    @Autowired
    ArticleRejectService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("articles/{articleId}/rejects")
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleReject rejectArticle(@PathVariable Long articleId, @RequestBody PostArticleRejectDTO articleRejectDTO) throws ClientErrorException {
        return service.reject(articleId, articleRejectDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("articles/{articleId}/rejects")
    public List<ArticleReject> getArticleRejectHistory(@PathVariable Long articleId, HttpServletRequest request) throws ClientErrorException {
        return service.getByArticleId(articleId, request);
    }
}
