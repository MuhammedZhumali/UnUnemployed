package org.hire.job_platform_service.service;

import org.hire.job_platform_service.domain.model.Job;
import org.hire.job_platform_service.domain.repository.JobRepository;
import org.hire.job_platform_service.dto.JobDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<JobDto> getAllJobs(){
        return jobRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public JobDto getById(Long id){
        return toDto(jobRepository.findById(id).orElse(null));
    }

    public JobDto createJob(Job job){
        Job savedJob = jobRepository.save(job);
        return toDto(savedJob);
    }

    public void deleteJob(Long id){
        jobRepository.deleteById(id);
    }

    public JobDto toDto(Job job){
        JobDto dto = new JobDto();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setCompany(job.getCompany());
        dto.setLocation(job.getLocation());
        dto.setSalary(job.getSalary());
        return dto;
    }
}
