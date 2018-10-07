package com.krokogator.spring.config;

import com.krokogator.spring.resources.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

    @Autowired
    UserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getUserAuthentication().getPrincipal().toString());
        System.out.println(authentication.getCredentials().toString());
        //Here put additional DATA
        info.put("userId", userService.loadUserByUsername(authentication.getName()).getId());
        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);

        accessToken = super.enhance(customAccessToken, authentication);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(new HashMap<>());

        return accessToken;

    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication authentication = super.extractAuthentication(map);
        IdAwareOAuth2Request idAwareOAuth2Request = new IdAwareOAuth2Request(authentication.getOAuth2Request());
        idAwareOAuth2Request.setUserId(Long.valueOf((Integer)map.get("userId")));
        return new OAuth2Authentication(idAwareOAuth2Request, authentication.getUserAuthentication());
    }
}
