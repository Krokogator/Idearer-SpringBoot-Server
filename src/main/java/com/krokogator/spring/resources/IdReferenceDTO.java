package com.krokogator.spring.resources;

import javax.validation.constraints.NotNull;


public class IdReferenceDTO {

    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




}
