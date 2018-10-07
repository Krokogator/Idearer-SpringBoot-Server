package com.krokogator.spring.resources.category.dto;

import com.krokogator.spring.resources.category.validationgroup.PostCategoryValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostCategoryDTO {

    @NotNull(groups = PostCategoryValidation.class)
    @Size(min = 1, max = 50)
    public String name;
}
