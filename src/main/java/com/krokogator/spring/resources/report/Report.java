package com.krokogator.spring.resources.report;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Try to apply this for general use
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "REPORT_TYPE")
public abstract class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 10, max = 200)
    private String description;

    public Report() {
    }

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
}
