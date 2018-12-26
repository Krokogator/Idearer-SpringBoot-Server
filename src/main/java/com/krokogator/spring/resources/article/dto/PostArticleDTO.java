package com.krokogator.spring.resources.article.dto;

import com.krokogator.spring.resources.shared.IdReferenceDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostArticleDTO {

    @NotNull
    @Size(min = 1)
    public String title;

    @NotNull
    @Size(min = 1)
    public String content;

    @NotNull
    @Valid
    private IdReferenceDTO category;

    public IdReferenceDTO getCategory() {
        return category;
    }

    public void setCategory(IdReferenceDTO category) {
        this.category = category;
    }
}


