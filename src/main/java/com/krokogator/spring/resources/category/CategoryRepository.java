package com.krokogator.spring.resources.category;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, String> {
    Boolean existsCategoryByTitle(String title);

    Optional<Category> findByTitle(String title);

    List<Category> findAll();
}
