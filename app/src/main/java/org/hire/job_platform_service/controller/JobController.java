package org.hire.job_platform_service.controller;

import org.hire.job_platform_service.service.JobService;
import org.hire.job_platform_service.dto.JobDto;
import org.hire.job_platform_service.domain.model.Job;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/create")
    public JobDto createJob(@RequestBody Job job){
        return jobService.createJob(job);
    }

    @GetMapping("/getAll")
    public List<JobDto> getAllJobs(){
        return jobService.getAllJobs();
    }

    @GetMapping("/get/{id}")
    public JobDto getById(@PathVariable Long id){
        return jobService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        jobService.deleteJob(id);
    }


}