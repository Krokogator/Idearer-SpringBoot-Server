package com.krokogator.spring.resources.user;

import com.krokogator.spring.resources.user.dto.GetUserDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@Api(tags = "Users")
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public GetUserDTO registerUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/login")
    @RolesAllowed({"ADMIN", "USER"})
    public void loginUser(){
        // 200 OK if credentials are ok
    }

    @GetMapping
    public List<GetUserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    public GetUserDTO getUser(@PathVariable Long id){
        return userService.getUser(id);
    }
}
