package com.krokogator.spring.resources.report.article;

import com.krokogator.spring.resources.article.dto.ArticleDTO;
import com.krokogator.spring.resources.user.User;
import com.krokogator.spring.resources.user.dto.UserDTO;
import com.krokogator.spring.utils.DTO;
import com.krokogator.spring.utils.EntityDTO;

public class ArticleReportDTO {

    public static class MinimalItem implements EntityDTO<ArticleReport> {
        public Long id;

        @Override
        public void toDto(ArticleReport articleReport) {
            this.id = articleReport.getId();
        }
    }

    public static class Item extends MinimalItem {
        public String description;
        public UserDTO.ListItem user;

        @Override
        public void toDto(ArticleReport articleReport) {
            super.toDto(articleReport);
            this.description = articleReport.getDescription();
            this.user = DTO.from(articleReport.getReportAuthor(), UserDTO.ListItem.class);
        }
    }
}
