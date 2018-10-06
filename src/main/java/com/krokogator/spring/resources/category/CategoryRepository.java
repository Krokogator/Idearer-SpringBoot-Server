package com.krokogator.spring.resources.category;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Boolean existsCategoryByName(String name);

    Category getById(long id);

    List<Category> findAll();
}
