package com.krokogator.spring.resources.report.article;

import com.krokogator.spring.resources.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PreAuthorize("hasRole('ADMIN')")
public class ArticleReportService extends ReportService<ArticleReport> {

    @Autowired
    ArticleReportRepository articleReportRepository;

    public void deleteAllByArticleId(Long articleId) {
        articleReportRepository.deleteAllByArticleId(articleId);
    }

    public Page<ArticleReport> findByArticleId(Long articleId, Pageable page) {
        return articleReportRepository.findAllByArticleId(articleId, page);
    }

    public List<Long> findReportedArticles() {
        return articleReportRepository.findDistinctArticles();
    }
}
