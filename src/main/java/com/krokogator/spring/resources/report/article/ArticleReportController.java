package com.krokogator.spring.resources.report.article;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "Reports")
public class ArticleReportController {

    @Autowired
    ArticleReportService reportService;

    @PostMapping("articles/{articleId}/reports")
    public ArticleReport add(@RequestBody ArticleReport report, @RequestParam Long articleId) {
        return reportService.save(report, articleId);
    }

    @GetMapping("articles/reports/{id}")
    public ArticleReport getById(@RequestParam Long id) {
        return reportService.findById(id);
    }

    @GetMapping("articles/reports")
    public List<ArticleReport> getAll() {
        return reportService.findAll();
    }

    @DeleteMapping("articles/reports/{id}")
    public void deleteById(@PathVariable Long id) {
        reportService.deleteById(id);
    }
}
