package com.krokogator.spring.resources.article.dto;

import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.article.ArticleStatus;
import com.krokogator.spring.resources.category.Category;
import com.krokogator.spring.resources.report.article.ArticleReportDTO;
import com.krokogator.spring.resources.user.User;
import com.krokogator.spring.resources.user.dto.UserDTO;
import com.krokogator.spring.utils.DTO;
import com.krokogator.spring.utils.EntityDTO;

import java.util.Date;
import java.util.List;

public class ArticleDTO {

    public static class MinimalItem implements EntityDTO<Article> {
        public Long id;

        @Override
        public void toDto(Article article) {
            this.id = article.getId();
        }
    }

    public static class ListItem extends MinimalItem {
        public String title;

        @Override
        public void toDto(Article article) {
            super.toDto(article);
            this.title = article.getTitle();
        }
    }

    public static class Item extends ListItem {
        public String content;
        public Date created;
        public Integer likesCount;
        public Boolean liked;
        public Integer commentsCount;
        public UserDTO.MinimalItem user;
        public Category category;
        public ArticleStatus status;

        @Override
        public void toDto(Article article) {
            super.toDto(article);
            this.content = article.getContent();
            this.created = article.getCreated();
            this.likesCount = article.getLikesCount();
            this.liked = article.isLiked();
            this.commentsCount = article.getCommentsCount();
            this.user = DTO.from(article.getUser(), UserDTO.MinimalItem.class);
            this.category = article.getCategory();
            this.status = article.getStatus();
        }
    }

    public static class ReportedItem extends Item {
        public List<ArticleReportDTO.Item> reports;

        @Override
        public void toDto(Article article) {
            super.toDto(article);
            this.reports = DTO.from(article.getReports(), ArticleReportDTO.Item.class);
        }
    }

}