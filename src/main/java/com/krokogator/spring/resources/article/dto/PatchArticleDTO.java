package com.krokogator.spring.resources.article.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.krokogator.spring.resources.article.ArticleStatus;
import com.krokogator.spring.resources.shared.IdReferenceDTO;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class PatchArticleDTO {

    @Size(min = 1)
    public String title;

    @Size(min = 1)
    public String content;

    public Boolean liked;

    public ArticleStatus status;

    @Valid
    private IdReferenceDTO category;

    public IdReferenceDTO getCategory() {
        return category;
    }

    public void setCategory(IdReferenceDTO category) {
        this.category = category;
    }

    @JsonIgnore
    public Boolean isEmpty() {
        if (title != null) return false;
        if (content != null) return false;
        return category == null;
    }
}


