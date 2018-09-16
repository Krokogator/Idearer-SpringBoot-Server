package com.krokogator.spring.resources.category;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.category.projection.RequestBodyCategory;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@Api(tags = "Categories")
@RequestMapping(value = "/categories")
@RolesAllowed({"ROLE_ADMIN"})
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public Category addCategory(@RequestBody Category category) throws ClientErrorException {
        return categoryService.addCategory(category);
    }

    @PatchMapping(value = "/{id}")
    public Category updateCategory(@RequestBody RequestBodyCategory category, @PathVariable long id) throws ClientErrorException {
        Category categoryDB = new Category();
        categoryDB.setName(category.name);
        return categoryService.updateCategory(id, categoryDB);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCategory(@PathVariable long id) throws ClientErrorException {
        categoryService.deleteCategory(id);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
