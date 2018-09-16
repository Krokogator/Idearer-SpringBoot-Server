package com.krokogator.spring.resources.folder;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.folder.projection.FolderProjectionWithoutNoteContent;
import com.krokogator.spring.resources.folder.validation.FolderRequestBodyValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Folders")

@RequestMapping(value = "/folders")
public class FolderController {
    @Autowired
    FolderService folderService;

    @Autowired
    FolderRequestBodyValidator folderRequestBodyValidator;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FolderProjectionWithoutNoteContent.class)
    })
    @GetMapping
    public List<FolderProjectionWithoutNoteContent> getAllFoldersAndNotesWithoutNoteContent() {
        return folderService.getAllFolders();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/{parentFolderId}")
    public Folder addFolder(@RequestBody Folder folder, @PathVariable long parentFolderId) throws ClientErrorException {
        folderRequestBodyValidator.validateAddFolder(folder);
        Folder createdFolder = folderService.addFolder(folder, parentFolderId);
        return createdFolder;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found")
    })
    @DeleteMapping(value = "/{id}")
    public void deleteFolder(@PathVariable Long id) throws ClientErrorException {
        folderService.deleteFolder(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PutMapping(value = "/{folderId}/move/{parentFolderId}")
    public void moveFolder(@PathVariable Long folderId, @PathVariable Long parentFolderId) throws ClientErrorException {
        folderService.moveFolder(folderId, parentFolderId);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PutMapping(value = "/{folderId}")
    public Folder updateFolder(@PathVariable Long folderId, @RequestBody Folder folder) throws ClientErrorException {
        folderRequestBodyValidator.validateUpdateFolder(folder);
        return folderService.updateFolder(folderId, folder);
    }
}
