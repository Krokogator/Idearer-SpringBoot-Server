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
        if(categoryRepository.findByTitle(category.getTitle()).isPresent()) {
            throw new ClientErrorException(HttpStatus.CONFLICT, "Category '"+category.getTitle()+"' alread exists.");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(String title, Category category) throws ClientErrorException {
         Category categoryDb = categoryRepository.findByTitle(title).orElseThrow(()-> new ClientErrorException(HttpStatus.NOT_FOUND, "Category not found"));

        if(category.getName() != null){
            categoryDb.setName(category.getName());
        }

        //Get category if exists
        Category category = categoryRepository.findByTitle(titleId).orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Category '"+titleId+"' not found."));

        //Update category title
        category.setTitle(dto.getTitle());

        return categoryRepository.save(category);
    }

    public void deleteCategory(String title) throws ClientErrorException {
        categoryRepository.deleteById(title);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
