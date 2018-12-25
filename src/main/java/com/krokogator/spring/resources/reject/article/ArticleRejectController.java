package com.krokogator.spring.resources.reject.article;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.reject.article.dto.PostArticleRejectDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@Api(tags = "Rejects")
public class ArticleRejectController {

    @Autowired
    ArticleRejectService service;

    @PostMapping("articles/{articleId}/rejects")
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleReject reject(@PathVariable Long articleId, @RequestBody PostArticleRejectDTO dto) throws ClientErrorException {
        return service.reject(articleId, dto);
    }

    @GetMapping("articles/{articleId}/rejects")
    public List<ArticleReject> getByArticleId(@PathVariable Long articleId) {
        return service.getByArticleId(articleId);
    }
}
