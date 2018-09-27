package com.krokogator.spring.resources.user.dto;

import com.krokogator.spring.resources.user.validationgroup.PostUserValidation;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiModel(description = "Model used for creating User")
public class PatchUserDTO {

    @NotNull(groups = PostUserValidation.class)
    @Size(min = 3, max = 20)
    public String username;

    @NotNull(groups = PostUserValidation.class)
    @Email
    public String email;

    @NotNull(groups = PostUserValidation.class)
    @Size(min = 6, max = 128)
    public String password;

    @Pattern(regexp = "^(ADMIN|USER)$")
    public String role;
}
