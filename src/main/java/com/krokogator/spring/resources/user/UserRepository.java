package com.krokogator.spring.resources.user;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsernameIgnoreCase(String username);

    User getById(Long id);
}