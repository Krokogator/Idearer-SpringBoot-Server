package com.krokogator.spring.resources.user;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.user.dto.GetUserDTO;
import com.krokogator.spring.resources.user.dto.PatchUserDTO;
import com.krokogator.spring.resources.user.dto.PostUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder PASSWORD_ENCODER;

    public GetUserDTO saveUser(PostUserDTO dto) throws ClientErrorException {
        Optional<User> duplicate = userRepository.findByUsernameIgnoreCase(dto.username);
        if (duplicate.isPresent()) {
            throw new ClientErrorException(HttpStatus.CONFLICT, "username already exists");
        }
        User user = new User();
        user.setUsername(dto.username);
        user.setPassword(PASSWORD_ENCODER.encode(dto.password));
        user.setEmail(dto.email);
        user.setRole("USER");
        return userRepository.save(user);

    }

    public GetUserDTO saveAdmin(PatchUserDTO dto) {
        User user = new User();
        user.setUsername(dto.username);
        user.setPassword(PASSWORD_ENCODER.encode(dto.password));
        user.setEmail(dto.email);
        user.setRole("ADMIN");
        return userRepository.save(user);
    }

    public List<GetUserDTO> getAllUsers() {
        List<GetUserDTO> allUsers = new ArrayList<>();
        userRepository.findAll().forEach(allUsers::add);
        return allUsers;
    }

    public GetUserDTO getUser(Long id) throws ClientErrorException {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "User '"+id+"' not found."));
    }

    @PreAuthorize("hasRole('ADMIN') OR @CurrentUser.getId() == #id AND #dto.role == NULL")
    public GetUserDTO patchUser(Long id, PatchUserDTO dto) {
        User user = userRepository.getById(id);
        Optional.ofNullable(dto.username).ifPresent(user::setUsername);
        Optional.ofNullable(dto.password).ifPresent(x -> user.setPassword(PASSWORD_ENCODER.encode(x)));
        Optional.ofNullable(dto.email).ifPresent(user::setEmail);
        Optional.ofNullable(dto.role).ifPresent(user::setRole);

        return userRepository.save(user);
    }

    @Override
    public SecureUser loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> temp = userRepository.findByUsernameIgnoreCase(username);
        User user = temp.orElseThrow(() -> new UsernameNotFoundException(username + " was not found"));

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
