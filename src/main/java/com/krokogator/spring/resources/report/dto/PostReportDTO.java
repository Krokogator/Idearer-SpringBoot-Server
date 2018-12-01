package com.krokogator.spring.resources.report.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class PostReportDTO {

    @NotNull
    @Length(min = 4, max = 200)
    public String description;
}
