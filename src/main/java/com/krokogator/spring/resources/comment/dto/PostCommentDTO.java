package com.krokogator.spring.resources.comment.dto;

import com.krokogator.spring.resources.shared.IdReferenceDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostCommentDTO {

    @NotNull
    @Size(min = 1)
    public String content;

    //@Valid field requires getters and setters to be handled as 400 Bad Request
    @NotNull
    @Valid
    private IdReferenceDTO article;

    @Valid
    private IdReferenceDTO parentComment;

    public IdReferenceDTO getArticle() {
        return article;
    }

    public void setArticle(IdReferenceDTO article) {
        this.article = article;
    }


    public IdReferenceDTO getParentComment() {
        return parentComment;
    }

    public void setParentComment(IdReferenceDTO parrentComment) {
        this.parentComment = parrentComment;
    }
}
