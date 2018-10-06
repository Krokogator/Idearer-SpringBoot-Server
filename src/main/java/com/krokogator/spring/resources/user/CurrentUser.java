package com.krokogator.spring.resources.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("CurrentUser")
public class CurrentUser {

    public static String getName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String name;

        if(authentication.getAuthorities().stream().anyMatch(x -> x.getAuthority().equals("ROLE_ANONYMOUS"))){
            name = "";
        } else {
            name = authentication.getName();
        }
        
        return name;
    }
}
