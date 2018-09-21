package com.krokogator.spring.resources.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.krokogator.spring.resources.user.validation.GetUser;
import com.krokogator.spring.resources.user.validation.PostUser;
import io.swagger.annotations.ApiModelProperty;
import javafx.geometry.Pos;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Entity(name = "userX")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Email
    @NotNull(groups = PostUser.class)
    private String email;

    private String username;

    @JsonIgnore
    @Size(min = 6)
    @NotNull(groups = PostUser.class)
    private String password;

    @JsonIgnore
    private String[] roles;


    public User() {
    }

    /*
    public User(String username, String password, String[] roles) {
        this.username = username;
        setPassword(password);
        this.roles = roles;
    }*/

    public User(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername( String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}