package com.krokogator.spring.resources.comment.dto;

import com.krokogator.spring.resources.IdReferenceDTO;
import com.krokogator.spring.resources.comment.validationgroup.PatchCommentValidation;
import com.krokogator.spring.resources.comment.validationgroup.PostCommentValidation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class PostCommentDTO {

    @NotNull(groups = PostCommentValidation.class)
    @Size(min = 1)
    public String content;

    //@Valid field requires getters and setters to be handled as 400 Bad Request
    @NotNull(groups = PostCommentValidation.class)
    @Null(groups = PatchCommentValidation.class)
    @Valid
    private IdReferenceDTO article;

    @Null(groups = PostCommentValidation.class)
    public Boolean liked;

    public IdReferenceDTO getArticle() {
        return article;
    }

    public void setArticle(IdReferenceDTO article) {
        this.article = article;
    }




}
