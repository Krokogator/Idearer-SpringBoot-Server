package com.krokogator.spring.resources.user;

import com.krokogator.spring.resources.user.validation.PostUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.groups.Default;
import java.util.List;

@RestController
@Api(tags = "Users")
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", required = true, dataType = "UserPost", paramType = "body")
    })
    public User registerUser(@Validated ({PostUser.class, Default.class}) @RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/login")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public void loginUser(){

    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }
}
