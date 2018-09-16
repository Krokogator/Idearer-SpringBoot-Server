package com.krokogator.spring.resources.folder.validation;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.error.client.ClientErrorResposeThrower;
import com.krokogator.spring.resources.folder.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FolderRequestBodyValidator {

    private final static int maxNameLength = 20;


    @Autowired
    ClientErrorResposeThrower clientErrorResposeThrower;

    public void validateAddFolder(Folder folder) throws ClientErrorException {
        validateName(folder.getName());
    }

    public void validateUpdateFolder(Folder folder) throws ClientErrorException {
        validateName(folder.getName());
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
