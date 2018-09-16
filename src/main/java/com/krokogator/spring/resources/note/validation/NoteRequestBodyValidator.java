package com.krokogator.spring.resources.note.validation;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.error.client.ClientErrorResposeThrower;
import com.krokogator.spring.resources.note.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NoteRequestBodyValidator {

    private final static int maxNameLength = 20;

    @Autowired
    ClientErrorResposeThrower clientErrorResposeThrower;

    public void validateAddNote(Note note) throws ClientErrorException {
        validateName(note.getName());
    }

    public void validateUpdateNote(Note note) throws ClientErrorException {
        validateName(note.getName());
        validateContent(note.getContent());
    }

    private void validateContent(String content) throws ClientErrorException {
        if(content == null){
            clientErrorResposeThrower.throwBadRequest("{content} cannot be empty");
        }
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
