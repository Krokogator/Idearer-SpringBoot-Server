package com.krokogator.spring.resources.folder.projection;

import com.krokogator.spring.resources.note.projection.NoteProjectionWithoutContent;

import java.util.List;

public interface FolderProjectionWithoutNoteContent {
    long getId();
    String getName();
    List<FolderProjectionWithoutNoteContent> getFolders();
    List<NoteProjectionWithoutContent> getNotes();
}