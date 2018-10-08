package com.krokogator.spring.resources.article.dto;

import com.krokogator.spring.resources.IdReferenceDTO;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class PatchArticleDTO {

    @Size(min = 1)
    public String title;

    @Size(min = 1)
    public String content;

    public Boolean liked;

    @Valid
    private IdReferenceDTO category;

    public IdReferenceDTO getCategory() {
        return category;
    }

    public void setCategory(IdReferenceDTO category) {
        this.category = category;
    }
}


