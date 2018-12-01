package com.krokogator.spring.resources.report;

import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.comment.Comment;
import com.krokogator.spring.resources.report.article.ArticleReport;
import com.krokogator.spring.resources.report.comment.CommentReport;
import com.krokogator.spring.resources.report.dto.PostReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;

    private void addReport(Report report) {
        reportRepository.save(report);
    }

    public void addArticleReport(PostReportDTO report, Long articleId) {
        ArticleReport articleReport = new ArticleReport();
        articleReport.setDescription(report.description);
        articleReport.setArticle(new Article(articleId));
        reportRepository.save(articleReport);
    }

    public void addCommentReport(PostReportDTO report, Long commentId) {
        CommentReport commentReport = new CommentReport();
        commentReport.setDescription(report.description);
        commentReport.setComment(new Comment(commentId));
        reportRepository.save(commentReport);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }
}
