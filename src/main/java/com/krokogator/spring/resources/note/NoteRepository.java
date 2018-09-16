package com.krokogator.spring.resources.note;

import com.krokogator.spring.resources.note.projection.NoteProjectionOnlyContent;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, Long>{
    NoteProjectionOnlyContent getById(long id);
}
