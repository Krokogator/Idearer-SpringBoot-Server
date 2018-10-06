package com.krokogator.spring.resources.category;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Category {

    @Id
    private String title;

    public Category(){}

    public Category(String name ) {
        this.title = name; }

    public String getName() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }
}
