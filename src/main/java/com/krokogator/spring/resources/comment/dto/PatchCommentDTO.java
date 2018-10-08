package com.krokogator.spring.resources.comment.dto;

import javax.validation.constraints.Size;

public class PatchCommentDTO {

    @Size(min = 1)
    public String content;

    public Boolean liked;

}
