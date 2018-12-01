package com.krokogator.spring.resources.report;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportRepository extends CrudRepository<Report, Long> {

    List<Report> findAll();
}
