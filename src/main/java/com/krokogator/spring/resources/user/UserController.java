package com.krokogator.spring.resources.user;

import com.krokogator.spring.resources.user.dto.GetUserDTO;
import com.krokogator.spring.resources.user.dto.PostPatchUserDTO;
import com.krokogator.spring.resources.user.dto.PostUserValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.List;

@RestController
@Api(tags = "Users")
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict")
    })
    public GetUserDTO registerUser(@RequestBody @Validated({PostUserValidation.class, Default.class}) PostPatchUserDTO user){
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    public void loginUser(){
        // 200 OK if credentials are ok
    }

    @GetMapping
    public List<GetUserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found")
    })
    public GetUserDTO getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public void patchUser(@PathVariable Long id, @RequestBody @Validated PostPatchUserDTO user) {
        userService.patchUser(id, user);
    }
}
