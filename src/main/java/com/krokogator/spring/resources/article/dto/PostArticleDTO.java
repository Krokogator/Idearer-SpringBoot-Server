package com.krokogator.spring.resources.article.dto;

import com.krokogator.spring.resources.IdReferenceDTO;
import com.krokogator.spring.resources.article.validationgroup.PostArticleValidation;
import com.krokogator.spring.resources.comment.validationgroup.PostCommentValidation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class PostArticleDTO {

    @NotNull(groups = PostArticleValidation.class)
    @Size(min = 1)
    public String title;

    @NotNull(groups = PostArticleValidation.class)
    @Size(min = 1)
    public String content;

    @Null(groups = PostArticleValidation.class)
    public Boolean liked;

    @Valid
    @NotNull(groups = PostArticleValidation.class)
    private IdReferenceDTO category;

    public IdReferenceDTO getCategory() {
        return category;
    }

    public void setCategory(IdReferenceDTO category) {
        this.category = category;
    }
}


