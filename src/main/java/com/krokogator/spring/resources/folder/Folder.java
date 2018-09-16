package com.krokogator.spring.resources.folder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.krokogator.spring.resources.note.Note;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

@Entity
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(readOnly = true)
    private long id;

    private String name;

    //Folders inside folder
    @JsonIgnore
    @ApiModelProperty(readOnly = true)
    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL)
    @OrderBy("name ASC")
    private List<Folder> folders;

    //Notes inside folder
    @JsonIgnore
    @ApiModelProperty(readOnly = true)
    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    @OrderBy("name ASC")
    private List<Note> notes;

    //Parent folder, if null then root directory
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(nullable = true)
    private Folder parentFolder;

    public Folder(){}

    public Folder(long id){ this.id = id; }

    public long getId() {
        return id;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder folder) {
        this.parentFolder = folder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
