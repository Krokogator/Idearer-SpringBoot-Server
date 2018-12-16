package com.krokogator.spring.resources.report.article;

import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleReportService extends ReportService<ArticleReport> {

    @Autowired
    ArticleReportRepository articleReportRepository;

    public ArticleReport save(ArticleReport report, Long articleId) {
        report.setArticle(new Article(articleId));
        return save(report);
    }
}
