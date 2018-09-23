package com.krokogator.spring.resources.user;

import com.krokogator.spring.resources.user.dto.GetUserDTO;
import com.krokogator.spring.resources.user.dto.PostPatchUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public GetUserDTO saveUser(PostPatchUserDTO dto){
        User user = new User();
        user.setUsername(dto.username);
        user.setPassword(User.PASSWORD_ENCODER.encode(dto.password));
        user.setEmail(dto.email);
        user.setRoles(new String[]{"ROLE_USER"});
        return userRepository.save(user);

    }

    public GetUserDTO saveAdmin(PostPatchUserDTO dto){
        User user = new User();
        user.setUsername(dto.username);
        user.setPassword(User.PASSWORD_ENCODER.encode(dto.password));
        user.setEmail(dto.email);
        user.setRoles(new String[]{"ROLE_ADMIN"});
        return userRepository.save(user);
    }

    public List<GetUserDTO> getAllUsers() {
        List<GetUserDTO> allUsers = new ArrayList<>();
        userRepository.findAll().forEach(allUsers::add);
        return allUsers;
    }

    public GetUserDTO getUser(Long id) {
        return userRepository.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN') OR @loggedInUser.getId() == #id")
    public GetUserDTO patchUser(Long id, PostPatchUserDTO dto) {
        User user = userRepository.getById(id);
        user.setUsername((dto.username == null)? user.getUsername() : dto.username);
        user.setPassword(User.PASSWORD_ENCODER.encode((dto.password == null)? user.getPassword() : dto.password));
        user.setEmail((dto.email == null)? user.getEmail() : dto.email);
        return userRepository.save(user);
    }
}
