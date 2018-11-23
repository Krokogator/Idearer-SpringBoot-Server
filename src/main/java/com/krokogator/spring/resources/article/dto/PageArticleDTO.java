package com.krokogator.spring.resources.article.dto;

import com.krokogator.spring.resources.article.Article;

import java.util.List;

public class PageArticleDTO {
    public List<Article> content;
    public Integer page;
    public Integer pageSize;
    public long lastPage;
}
