package com.krokogator.spring.config.jwt;


import org.springframework.security.oauth2.provider.OAuth2Request;

public class IdAwareOAuth2Request extends OAuth2Request {

    public IdAwareOAuth2Request(OAuth2Request other){
        super(other);
    }

    private Long userId;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}

