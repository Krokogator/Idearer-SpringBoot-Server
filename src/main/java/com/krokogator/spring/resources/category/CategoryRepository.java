package com.krokogator.spring.resources.category;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Boolean existsCategoryByName(String name);

    Category getById(long id);

    Optional<Category> findByNameIgnoreCase(String title);

    List<Category> findAll();
}
