package com.krokogator.spring.resources.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.krokogator.spring.resources.user.dto.GetUserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;


@Entity(name = "userX")
public class User implements GetUserDTO{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private String email;

    private String username;

    @Column(unique = true)
    private String usernameLowerCase;

    @PrePersist
    @PreUpdate
    private void prepare() {
        this.usernameLowerCase = (username == null) ? null : username.toLowerCase();
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String role;


    public User() {
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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