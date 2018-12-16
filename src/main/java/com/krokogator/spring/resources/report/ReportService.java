package com.krokogator.spring.resources.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService<T extends Report> {

    @Autowired
    ReportRepository<T> reportRepository;


    public T save(T report) {
        return reportRepository.save(report);
    }

    public T findById(Long id) {
        return reportRepository.findById(id).get();
    }

    public List<T> findAll() {
        return reportRepository.findAll();
    }

    public void deleteById(Long id) {
        reportRepository.deleteById(id);
    }
}
