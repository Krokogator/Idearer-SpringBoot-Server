package com.krokogator.spring.resources.report;

import com.krokogator.spring.resources.report.dto.PostReportDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "Reports")
public class ReportController {

    @Autowired
    ReportService reportService;

    @PostMapping("/articles/{id}/reports")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public void reportArticle(@RequestBody PostReportDTO dto, @PathVariable Long id) {
        reportService.addArticleReport(dto, id);
    }

    @PostMapping("/comments/{id}/reports")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public void reportComment(@RequestBody PostReportDTO dto, @PathVariable Long id) {
        reportService.addCommentReport(dto, id);
    }

    @GetMapping("/reports")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }
}
