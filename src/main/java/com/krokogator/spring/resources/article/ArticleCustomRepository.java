package com.krokogator.spring.resources.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleCustomRepository {

    Page<Article> getArticlesByAdvancedQuery(Long userId, String categoryName, ArticleStatus status, Pageable page, ArticleSort sort);
}
