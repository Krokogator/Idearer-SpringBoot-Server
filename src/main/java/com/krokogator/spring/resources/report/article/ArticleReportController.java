package com.krokogator.spring.resources.report.article;

import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.article.ArticleService;
import com.krokogator.spring.resources.article.dto.ArticleDTO;
import com.krokogator.spring.resources.report.dto.PostReportDTO;
import com.krokogator.spring.utils.DTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"Reports"})
public class ArticleReportController {

    @Autowired
    ArticleReportService reportService;

    @Autowired
    ArticleService articleService;

    @PostMapping("articles/{articleId}/reports")
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleReport reportArticle(@RequestBody PostReportDTO reportDTO, @PathVariable Long articleId) {
        ArticleReport report = new ArticleReport();
        report.setDescription(reportDTO.description);
        report.setArticle(new Article(articleId));
        return reportService.save(report);
    }

    @DeleteMapping("articles/reports/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReport(@PathVariable Long id) {
        reportService.deleteById(id);
    }

    @GetMapping("articles/reports")
    public Page<ArticleDTO.ReportedItem> getReportedArticles(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize
    ) {
        return DTO.from(articleService.findReported(page, pageSize), ArticleDTO.ReportedItem.class);
    }
}
