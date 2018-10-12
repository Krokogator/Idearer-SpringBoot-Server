package com.krokogator.spring.resources.category;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.category.dto.PatchCategoryDTO;
import com.krokogator.spring.resources.category.dto.PostCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;



    public Category addCategory(PostCategoryDTO dto) throws ClientErrorException {
        //Check if category name already exists
        if (categoryRepository.findByNameIgnoreCase(dto.name).isPresent()){
                throw new ClientErrorException(HttpStatus.CONFLICT, "Category '"+dto.name+"' already exists.");
        }

        Category category = new Category(dto.name);

        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, PatchCategoryDTO dto) throws ClientErrorException {
        //Get category if exists
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Category '"+id+"' not found."));

        if(categoryRepository.findByNameIgnoreCase(dto.name).isPresent()) {
            throw new ClientErrorException(HttpStatus.CONFLICT, "Category '"+dto.name+"' already exists.");
        }

        //Update category title
        if(dto.name != null) category.setName(dto.name);

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) throws ClientErrorException {
        categoryRepository.findById(id).orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Category '"+id+"' not found."));
        categoryRepository.deleteById(id);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
