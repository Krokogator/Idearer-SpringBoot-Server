package com.krokogator.spring.resources.category.dto;

import javax.validation.constraints.Size;

public class PatchCategoryDTO {

    @Size(min = 1, max = 50)
    public String name;
}
