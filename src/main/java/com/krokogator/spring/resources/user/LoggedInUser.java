package com.krokogator.spring.resources.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("loggedInUser")
public class LoggedInUser {

    public static Long getId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecureUser secureUser;
        try{
            secureUser = (SecureUser) authentication.getPrincipal();
        } catch (Exception e) {
            return 0L;
        }
        return secureUser.getId();
    }
}
