package com.krokogator.spring.resources.report;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportRepository<T extends Report> extends CrudRepository<T, Long> {
    List<T> findAll();
}
