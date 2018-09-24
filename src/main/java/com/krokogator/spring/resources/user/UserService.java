package com.krokogator.spring.resources.user;

import com.krokogator.spring.resources.user.dto.GetUserDTO;
import com.krokogator.spring.resources.user.dto.PostPatchUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public GetUserDTO saveUser(PostPatchUserDTO dto){
        User user = new User();
        user.setUsername(dto.username);
        user.setPassword(User.PASSWORD_ENCODER.encode(dto.password));
        user.setEmail(dto.email);
        user.setRole("USER");
        return userRepository.save(user);

    }

    public GetUserDTO saveAdmin(PostPatchUserDTO dto){
        User user = new User();
        user.setUsername(dto.username);
        user.setPassword(User.PASSWORD_ENCODER.encode(dto.password));
        user.setEmail(dto.email);
        user.setRole("ADMIN");
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

    @PreAuthorize("hasRole('ADMIN') OR @CurrentUser.getId() == #id")
    public GetUserDTO patchUser(Long id, PostPatchUserDTO dto) {
        User user = userRepository.getById(id);
        if (dto.username != null) {
            user.setUsername(dto.username);
        }
        if (dto.password != null) {
            user.setPassword(User.PASSWORD_ENCODER.encode(dto.password));
        }
        if (dto.email != null) {
            user.setEmail(dto.email);
        }
        return userRepository.save(user);
    }

    @Override
    public SecureUser loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
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
                AuthorityUtils.createAuthorityList("ROLE_" + user.getRole()),
                user.getId());

    }
}
