package com.krokogator.spring.resources.category.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostCategoryDTO {

    @NotNull @Size(min = 1, max = 50)
    public String name;
}
