package org.hire.job_platform_service.controller;

import org.springframework.web.bind.annotation.*;
import org.hire.job_platform_service.domain.model.JobApplications;
import org.hire.job_platform_service.dto.ApplyJobRequest;
import org.hire.job_platform_service.service.JobApplicationService;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class JobApplicationController {
    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }

    @GetMapping("/user/{userId}")
    public List<JobApplications> getApplications(@PathVariable Long userId) {
        return service.getUserApplications(userId);
    }

    @PostMapping("/apply")
    public JobApplications apply(@RequestBody ApplyJobRequest request) {
        return service.applyForJob(request.getUserId(), request.getJobId());
    }

    @DeleteMapping("/delete/{applicationId}")
    public void deleteJobApplication(@PathVariable Long applicationId) {
        service.deleteJobApplication(applicationId);
    }
}
