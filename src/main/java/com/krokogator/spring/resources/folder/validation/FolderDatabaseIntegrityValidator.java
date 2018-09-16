package com.krokogator.spring.resources.folder.validation;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.error.client.ClientErrorResposeThrower;
import com.krokogator.spring.resources.folder.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FolderDatabaseIntegrityValidator {

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    ClientErrorResposeThrower clientErrorResposeThrower;

    public void validateFolderExistanceIncludingRoot(Long parentFolderId) throws ClientErrorException {
        if(parentFolderId != 0 && !folderRepository.existsById(parentFolderId)){
            clientErrorResposeThrower.throwNotFound("{parentFolderId} == "+parentFolderId+" not found");
        }
    }

    public void validateFolderExistance(Long id) throws ClientErrorException {
        if(!folderRepository.existsById(id)){
            clientErrorResposeThrower.throwNotFound("{folderId} == "+id+" not found");
        }
    }
}
