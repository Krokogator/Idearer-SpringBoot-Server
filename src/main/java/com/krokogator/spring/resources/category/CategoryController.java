package com.krokogator.spring.resources.category;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.category.dto.PostCategoryDTO;
import com.krokogator.spring.resources.category.projection.RequestBodyCategory;
import com.krokogator.spring.resources.category.validationgroup.PostCategoryValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict")
    })
    public Category addCategory(@RequestBody @Validated({PostCategoryValidation.class, Default.class}) PostCategoryDTO dto) throws ClientErrorException {
        Category category = new Category();
        category.setName(dto.title);
        return categoryService.addCategory(category);
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict")
    })
    public Category updateCategory(@RequestBody @Validated PostCategoryDTO dto, @PathVariable Long id) throws ClientErrorException {
        Category category = new Category();
        category.setName(dto.title);
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict")
    })
    public void deleteCategory(@PathVariable long id) throws ClientErrorException {
        categoryService.deleteCategory(id);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
