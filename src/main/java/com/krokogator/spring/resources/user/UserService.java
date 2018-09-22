package com.krokogator.spring.resources.user;

import com.krokogator.spring.resources.user.dto.GetUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public GetUserDTO saveUser(User dto){

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        //Encode password
        user.setPassword(User.PASSWORD_ENCODER.encode(dto.getPassword()));
        user.setRoles(dto.getRoles());

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


}
