package com.krokogator.spring.resources.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DetailsService implements UserDetailsService {

    @Autowired
    UserRepository users;

    @Override
    public SecureUser loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = users.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException(username + " was not found");
        }

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new SecureUser(
                user.getUsername(),
                user.getPassword(),
        enabled,
        accountNonExpired,
        credentialsNonExpired,
        accountNonLocked,
        AuthorityUtils.createAuthorityList(user.getRoles()),
        user.getId());

    }
}