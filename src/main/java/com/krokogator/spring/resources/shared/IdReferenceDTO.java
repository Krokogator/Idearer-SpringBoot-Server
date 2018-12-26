package com.krokogator.spring.resources.shared;

import javax.validation.constraints.NotNull;


public class IdReferenceDTO {

    @NotNull
    private Long id;

    public IdReferenceDTO() {
    }

    public IdReferenceDTO(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




}
