package com.krokogator.spring.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.Map;

public class IdAwareAccessTokenConverter extends DefaultAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication authentication = super.extractAuthentication(map);
        IdAwareOAuth2Request idAwareOAuth2Request = new IdAwareOAuth2Request(authentication.getOAuth2Request());
        idAwareOAuth2Request.setUserId((Long) map.get("userId"));
        return new OAuth2Authentication(idAwareOAuth2Request, authentication.getUserAuthentication());
    }
}
