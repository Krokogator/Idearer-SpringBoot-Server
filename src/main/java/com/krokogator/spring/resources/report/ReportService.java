package com.krokogator.spring.resources.report;

import com.krokogator.spring.resources.user.CurrentUser;
import com.krokogator.spring.resources.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService<T extends Report> {

    @Autowired
    ReportRepository<T> reportRepository;

    @PreAuthorize("hasRole('USER')")
    public T save(T report) {
        report.setReportAuthor(new User(CurrentUser.getId()));
        return reportRepository.save(report);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public T findById(Long id) {
        return reportRepository.findById(id).get();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<T> findAll() {
        return reportRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id) {
        reportRepository.deleteById(id);
    }
}
