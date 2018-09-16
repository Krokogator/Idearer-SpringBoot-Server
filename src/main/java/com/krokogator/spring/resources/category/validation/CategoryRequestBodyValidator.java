package com.krokogator.spring.resources.category.validation;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.error.client.ClientErrorResposeThrower;
import com.krokogator.spring.resources.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryRequestBodyValidator {

    private final static int maxNameLength = 22;

    @Autowired
    ClientErrorResposeThrower clientErrorResposeThrower;

    public void validate(Category category) throws ClientErrorException{
        validateName(category.getName());
    }

    private void validateName(String name) throws ClientErrorException {
        //name is null or length of 0
        if(name == null || name.length() == 0){
            clientErrorResposeThrower.throwBadRequest("{name} cannot be empty");
        }
        //name exceeds maxNameLenght
        if(name.length() > maxNameLength){
            clientErrorResposeThrower.throwBadRequest("{name} cannot be longer than "+maxNameLength);
        }
    }
}
