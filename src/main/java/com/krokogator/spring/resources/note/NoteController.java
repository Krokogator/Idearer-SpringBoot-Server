package com.krokogator.spring.resources.note;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.note.projection.NoteProjectionOnlyContent;
import com.krokogator.spring.resources.note.validation.NoteRequestBodyValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Notes")
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    NoteRequestBodyValidator noteRequestBodyValidator;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(params = {"parentId"})
    public Note addNote(@RequestParam Long parentId, @RequestBody Note note) throws ClientErrorException {
        noteRequestBodyValidator.validateAddNote(note);
        return noteService.addNote(parentId, note);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = NoteProjectionOnlyContent.class),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping(value = "/{noteId}")
    public NoteProjectionOnlyContent getNoteContent(@PathVariable Long noteId) throws ClientErrorException {
        return noteService.getNoteContent(noteId);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found")
    })
    @DeleteMapping(value = "/{noteId}")
    public void deleteNote(@PathVariable Long noteId) throws ClientErrorException {
        noteService.deleteNote(noteId);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PutMapping(value = "/{noteId}/move/{folderId}")
    public void moveNote(@PathVariable Long noteId, @PathVariable Long folderId) throws ClientErrorException {
        noteService.moveNote(noteId, folderId);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PutMapping(value = "/{noteId}")
    public Note updateNote(@PathVariable Long noteId, @RequestBody Note note) throws ClientErrorException {
        noteRequestBodyValidator.validateUpdateNote(note);
        return noteService.updateNote(noteId, note);
    }
}
