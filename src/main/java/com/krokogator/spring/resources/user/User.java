package com.krokogator.spring.resources.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "userX")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String[] roles;


    public User() {
    }

    public User(String username, String password, String[] roles) {
        this.username = username;
        setPassword(password);
        this.roles = roles;
    }

    public User(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }
}