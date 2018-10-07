package com.krokogator.spring.resources.user;

import com.krokogator.spring.config.jwt.IdAwareOAuth2Request;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

@Component("CurrentUser")
public class CurrentUser {


    public static Long getId(){
        try{
            IdAwareOAuth2Request request = getOAuth2RequestFromAuthentication();
            return request.getUserId();
        } catch (Exception e){}
        try{
            SecureUser user = (SecureUser) getAuthentication().getPrincipal();
            return user.getId();
        } catch (ClassCastException e){}
        return 0L;
    }

    public static IdAwareOAuth2Request getOAuth2RequestFromAuthentication() {
        Authentication authentication = getAuthentication();
        return getTenantAwareOAuth2Request(authentication);
    }

    private static IdAwareOAuth2Request getTenantAwareOAuth2Request(Authentication authentication) {
        if (!authentication.getClass().isAssignableFrom(OAuth2Authentication.class)) {
            throw new RuntimeException("unexpected authentication object, expected OAuth2 authentication object");
        }
        return (IdAwareOAuth2Request) ((OAuth2Authentication) authentication).getOAuth2Request();
    }

    private static Authentication getAuthentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication();
    }
}
