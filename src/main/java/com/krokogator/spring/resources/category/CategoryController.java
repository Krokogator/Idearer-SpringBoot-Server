package com.krokogator.spring.resources.category;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.category.projection.RequestBodyCategory;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.List;

@RestController
@Api(tags = "Categories")
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Category addCategory(@RequestBody @Validated({PostCategoryValidation.class, Default.class}) PostCategoryDTO dto) throws ClientErrorException {
        Category category = new Category();
        category.setTitle(dto.title);
        return categoryService.addCategory(category);
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Category updateCategory(@RequestBody @Validated PostCategoryDTO dto, @PathVariable String title) throws ClientErrorException {
        Category category = new Category();
        category.setTitle(dto.title);
        return categoryService.updateCategory(title, category);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(@PathVariable long id) throws ClientErrorException {
        categoryService.deleteCategory(id);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
