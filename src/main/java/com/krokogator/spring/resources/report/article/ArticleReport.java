package com.krokogator.spring.resources.report.article;

import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.report.Report;
import com.krokogator.spring.resources.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ArticleReport extends Report {

    @ManyToOne
    private Article article;

    public ArticleReport() {
        super();
    }

    public ArticleReport(String description, User reportAuthor, Article article) {
        super();
        this.description = description;
        this.reportAuthor = reportAuthor;
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
