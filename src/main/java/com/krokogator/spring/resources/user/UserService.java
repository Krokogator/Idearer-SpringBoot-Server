package com.krokogator.spring.resources.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveUser(User user){
        //Encode password
        user.setPassword(User.PASSWORD_ENCODER.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        userRepository.findAll().forEach(allUsers::add);
        return allUsers;
    }

    public User getUser(Long id) {
        return userRepository.getById(id);
    }


}
