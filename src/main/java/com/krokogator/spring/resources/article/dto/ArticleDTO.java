package com.krokogator.spring.resources.article.dto;

import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.utils.EntityDTO;

public class ArticleDTO {

    public static class MinimalItem implements EntityDTO<Article> {
        public Long id;

        @Override
        public void toDto(Article entity) {

        }
    }
}
