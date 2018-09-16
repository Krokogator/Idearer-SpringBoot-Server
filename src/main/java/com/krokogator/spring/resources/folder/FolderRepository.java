package com.krokogator.spring.resources.folder;

import com.krokogator.spring.resources.folder.projection.FolderProjectionWithoutChildren;
import com.krokogator.spring.resources.folder.projection.FolderProjectionWithoutNoteContent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FolderRepository extends CrudRepository<Folder, Long> {
    List<FolderProjectionWithoutNoteContent> findAllByParentFolderId(Long id);
    FolderProjectionWithoutChildren getFolderById(Long id);
}
