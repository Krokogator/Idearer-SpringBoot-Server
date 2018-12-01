package com.krokogator.spring.resources.report.article;

import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.report.Report;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "ARTICLE")
public class ArticleReport extends Report {

    @ManyToOne
    private Article article;

    public ArticleReport() {
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Long getArticleId() {
        return article.getId();
    }

}
