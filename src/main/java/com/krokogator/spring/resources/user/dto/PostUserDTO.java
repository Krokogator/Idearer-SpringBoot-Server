package com.krokogator.spring.resources.user.dto;

import io.swagger.annotations.ApiModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(description = "Model used for creating User")
public class PostUserDTO {

    @NotNull
    @Size(min = 3, max = 20)
    public String username;

    @NotNull
    @Email
    public String email;

    @NotNull
    @Size(min = 6, max = 128)
    public String password;
}