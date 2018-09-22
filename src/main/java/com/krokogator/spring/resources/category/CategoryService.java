package com.krokogator.spring.resources.category;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.category.validation.CategoryDatabaseIntegrityValidator;
import com.krokogator.spring.resources.category.validation.CategoryRequestBodyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryDatabaseIntegrityValidator categoryDatabaseIntegrityValidator;

    @Autowired
    private CategoryRequestBodyValidator categoryRequestBodyValidator;

    public Category addCategory(Category category) throws ClientErrorException {
        categoryDatabaseIntegrityValidator.validateNameAlreadyExists(category.getName());
        return categoryRepository.save(category);
    }

    public Category updateCategory(long id, Category category) throws ClientErrorException {
        categoryDatabaseIntegrityValidator.validateExistance(id);
        Category categoryDb = categoryRepository.getById(id);

        if(category.getName() != null){
            categoryDb.setName(category.getName());
        }

        categoryRequestBodyValidator.validate(categoryDb);

        return categoryRepository.save(categoryDb);
    }

    public void deleteCategory(long id) throws ClientErrorException {
        categoryDatabaseIntegrityValidator.validateExistance(id);
        categoryRepository.deleteById(id);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
