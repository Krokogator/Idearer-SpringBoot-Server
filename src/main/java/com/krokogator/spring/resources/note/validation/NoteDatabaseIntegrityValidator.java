package com.krokogator.spring.resources.note.validation;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.error.client.ClientErrorResposeThrower;
import com.krokogator.spring.resources.note.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NoteDatabaseIntegrityValidator {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    ClientErrorResposeThrower clientErrorResposeThrower;

    public void validateNoteExistance(Long id) throws ClientErrorException {
        if(!noteRepository.existsById(id)){
            clientErrorResposeThrower.throwNotFound("{noteId} == "+id+" not found");
        }
    }
}
