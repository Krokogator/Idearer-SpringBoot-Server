package com.krokogator.spring.resources.article;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleCustomRepository {

    List<Article> getArticlesByAdvancedQuery(Long userId, String categoryName, Pageable page, ArticleSort sort);
}
