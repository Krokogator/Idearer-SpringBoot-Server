package com.krokogator.spring.resources.user.dto;

import io.swagger.annotations.ApiModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiModel(description = "Model used for creating User")
public class PatchUserDTO {

    @Size(min = 3, max = 20)
    public String username;

    @Email
    public String email;

    @Size(min = 6, max = 128)
    public String password;

    @Pattern(regexp = "^(ADMIN|USER)$")
    public String role;
}
