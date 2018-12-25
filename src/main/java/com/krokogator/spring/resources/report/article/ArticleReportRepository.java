package com.krokogator.spring.resources.report.article;

import com.krokogator.spring.resources.report.ReportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleReportRepository extends ReportRepository<ArticleReport> {
    void deleteAllByArticleId(Long articleId);

    Page<ArticleReport> findAllByArticleId(Long articleId, Pageable page);

    @Query("SELECT DISTINCT article.id FROM ArticleReport ")
    List<Long> findDistinctArticles();

}
