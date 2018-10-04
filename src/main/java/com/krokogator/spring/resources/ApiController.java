package com.krokogator.spring.resources;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.user.User;
import com.krokogator.spring.resources.user.UserService;
import com.krokogator.spring.resources.user.dto.PostUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;

@RestController
public class ApiController {

    @Autowired
    private UserService accountService;

    @GetMapping("/api/hello")
    public ResponseEntity<?> hello() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String msg = String.format("Hello %s", name);
        return new ResponseEntity<Object>(msg, HttpStatus.OK);
    }


    @PostMapping(path = "/api/register", produces = "application/json")
    public ResponseEntity<?> register(@RequestBody PostUserDTO account) throws ClientErrorException {
            return new ResponseEntity<Object>(
                    accountService.saveUser( account ), HttpStatus.OK);

    }



}
