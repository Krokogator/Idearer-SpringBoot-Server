package com.krokogator.spring.resources.report;

import com.krokogator.spring.resources.user.User;

import javax.persistence.*;

@MappedSuperclass
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String description;

    @ManyToOne
    protected User reportAuthor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getReportAuthor() {
        return reportAuthor;
    }

    public void setReportAuthor(User reportAuthor) {
        this.reportAuthor = reportAuthor;
    }
}
