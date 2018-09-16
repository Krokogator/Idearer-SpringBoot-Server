package com.krokogator.spring.resources.folder;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.folder.projection.FolderProjectionWithoutNoteContent;
import com.krokogator.spring.resources.folder.validation.FolderDatabaseIntegrityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FolderService {

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    FolderDatabaseIntegrityValidator folderDatabaseIntegrityValidator;

    public Folder addFolder(Folder folder, Long parentFolderId) throws ClientErrorException {
        folderDatabaseIntegrityValidator.validateFolderExistanceIncludingRoot(parentFolderId);

        //Set in root directory
        if(parentFolderId == 0){
            folder.setParentFolder(null);
        }
        //Set as a child of another folder
        else {
            folder.setParentFolder(new Folder(parentFolderId));
        }

        return folderRepository.save(folder);
    }

    public List<FolderProjectionWithoutNoteContent> getAllFolders(){
        List<FolderProjectionWithoutNoteContent> folders = new ArrayList<>();
        folderRepository.findAllByParentFolderId(null).forEach(folders::add);
        folders.sort(Comparator.comparing(FolderProjectionWithoutNoteContent::getName));
        return folders;
    }

    public void deleteFolder(Long id) throws ClientErrorException {
        folderDatabaseIntegrityValidator.validateFolderExistance(id);
        folderRepository.deleteById(id);
    }

    public void moveFolder(Long folderId, Long parentFolderId) throws ClientErrorException {
        folderDatabaseIntegrityValidator.validateFolderExistance(folderId);
        folderDatabaseIntegrityValidator.validateFolderExistanceIncludingRoot(parentFolderId);

        Folder folder = folderRepository.findById(folderId).get();

        //Set in root directory
        if(parentFolderId == 0){
            folder.setParentFolder(null);
        }
        //Set as a child of another folder
        else {
            folder.setParentFolder(new Folder(parentFolderId));
        }

        folderRepository.save(folder);
    }

    public Folder updateFolder(Long folderId, Folder folder) throws ClientErrorException {
        folderDatabaseIntegrityValidator.validateFolderExistance(folderId);

        Folder folderDB = folderRepository.findById(folderId).get();
        folderDB.setName(folder.getName());
        return folderRepository.save(folderDB);
    }
}
