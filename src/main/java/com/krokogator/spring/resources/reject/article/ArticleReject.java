package com.krokogator.spring.resources.reject.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.reject.Reject;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ArticleReject extends Reject {

    @ManyToOne
    @JsonIgnore
    private Article article;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
