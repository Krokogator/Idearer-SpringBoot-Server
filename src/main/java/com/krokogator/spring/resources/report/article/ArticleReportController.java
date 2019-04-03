package com.krokogator.spring.resources.report.article;

import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.report.dto.PostReportDTO;
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

    @PostMapping("articles/{articleId}/reports")
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleReport reportArticle(@RequestBody PostReportDTO reportDTO, @PathVariable Long articleId) {
        ArticleReport report = new ArticleReport();
        report.setDescription(reportDTO.description);
        report.setArticle(new Article(articleId));
        return reportService.save(report);
    }

    @GetMapping("articles/{articleId}/reports")
    public Page<ArticleReport> getArticleReports(@PathVariable Long articleId,
                                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return reportService.findByArticleId(articleId, pageable);
    }

    @GetMapping("articles/reported")
    public List<Long> getReportedArticlesIds() {
        return reportService.findReportedArticles();
    }

    @DeleteMapping("articles/reports/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReport(@PathVariable Long id) {
        reportService.deleteById(id);
    }
}
