package com.krokogator.spring.resources.category.validation;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.error.client.ClientErrorResposeThrower;
import com.krokogator.spring.resources.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryDatabaseIntegrityValidator {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ClientErrorResposeThrower clientErrorResposeThrower;

    public void validateNameAlreadyExists(String categoryName) throws ClientErrorException {
        if(categoryRepository.existsCategoryByName(categoryName)){
            clientErrorResposeThrower.throwNotFound("{name} == "+categoryName+" already exists");
        }
    }

    public void validateExistance(long id) throws ClientErrorException{
        if(!categoryRepository.existsById(id)){
            clientErrorResposeThrower.throwNotFound("{id} == "+id+" not found");
        }
    }
}
