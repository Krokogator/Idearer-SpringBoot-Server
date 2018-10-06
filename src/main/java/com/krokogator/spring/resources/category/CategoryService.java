package com.krokogator.spring.resources.category;

import com.krokogator.spring.error.client.ClientErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) throws ClientErrorException {
        if(categoryRepository.findByNameIgnoreCase(category.getName()).isPresent()) {
            throw new ClientErrorException(HttpStatus.CONFLICT, "Category '"+category.getId()+"' alread exists.");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category dto) throws ClientErrorException {
        //Get category if exists
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Category '"+id+"' not found."));

        if(categoryRepository.findByNameIgnoreCase(dto.getName()).isPresent()) {
            throw new ClientErrorException(HttpStatus.CONFLICT, "Category '"+dto.getName()+"' already exists.");
        }

        //Update category title
        category.setName(category.getName());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) throws ClientErrorException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Category '"+id+"' not found."));
        categoryRepository.delete(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
