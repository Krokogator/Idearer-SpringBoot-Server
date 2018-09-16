package com.krokogator.spring.resources.note;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.folder.Folder;
import com.krokogator.spring.resources.folder.FolderRepository;
import com.krokogator.spring.resources.folder.validation.FolderDatabaseIntegrityValidator;
import com.krokogator.spring.resources.note.projection.NoteProjectionOnlyContent;
import com.krokogator.spring.resources.note.validation.NoteDatabaseIntegrityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteDatabaseIntegrityValidator noteDatabaseIntegrityValidator;
    @Autowired
    private FolderDatabaseIntegrityValidator folderDatabaseIntegrityValidator;

    public Note addNote(Long folderId, Note note) throws ClientErrorException {
        folderDatabaseIntegrityValidator.validateFolderExistance(folderId);
        note.setFolder(new Folder(folderId));

        return noteRepository.save(note);
    }

    public NoteProjectionOnlyContent getNoteContent(Long id) throws ClientErrorException {
        noteDatabaseIntegrityValidator.validateNoteExistance(id);
        return noteRepository.getById(id);
    }

    public void deleteNote(Long id) throws ClientErrorException {
        noteDatabaseIntegrityValidator.validateNoteExistance(id);
        noteRepository.deleteById(id);
    }

    public void moveNote(Long noteId, Long folderId) throws ClientErrorException {
        noteDatabaseIntegrityValidator.validateNoteExistance(noteId);
        folderDatabaseIntegrityValidator.validateFolderExistance(folderId);
        Note note = noteRepository.findById(noteId).get();
        note.setFolder(new Folder(folderId));
        noteRepository.save(note);
    }

    public Note updateNote(Long noteId, Note note) throws ClientErrorException {
        noteDatabaseIntegrityValidator.validateNoteExistance(noteId);
        Note noteDB = noteRepository.findById(noteId).get();
        noteDB.setName(note.getName());
        noteDB.setContent(note.getContent());
        return noteRepository.save(noteDB);
    }
}
