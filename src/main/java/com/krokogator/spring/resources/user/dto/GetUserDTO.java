package com.krokogator.spring.resources.user.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(as=GetUserDTO.class)
public interface GetUserDTO {
    String getUsername();
    String getEmail();
    String getRole();
}
