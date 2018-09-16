package com.krokogator.spring.resources.note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.krokogator.spring.resources.folder.Folder;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(readOnly = true)
    private long id;

    private String name;

    //TODO Make content required in swagger, but visibile only in getContent request
    @Lob
    @Type(type = "text")
    private String content;

    //Parent folder, if null then root directory
    @ApiModelProperty(readOnly = true)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = true)
    private Folder folder;

    public Note(){}

    public Note(String name, String content){
        this.name = name;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}
