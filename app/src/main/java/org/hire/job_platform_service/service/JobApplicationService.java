package org.hire.job_platform_service.service;

import org.hire.job_platform_service.domain.model.Job;
import org.hire.job_platform_service.domain.model.User;
import org.hire.job_platform_service.domain.repository.JobApplicationRepository;
import org.hire.job_platform_service.domain.repository.JobRepository;
import org.hire.job_platform_service.domain.repository.UserRepository;
import org.hire.job_platform_service.domain.model.JobApplications;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public JobApplicationService(
            JobApplicationRepository jobApplicationRepository,
            UserRepository userRepository,
            JobRepository jobRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    public JobApplications applyForJob(Long userId, Long jobId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        if (hasApplied(user, job)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Already applied for this job");
        }
        JobApplications application = new JobApplications();
        application.setUser(user);
        application.setJob(job);
        return jobApplicationRepository.save(application);
    }

    public boolean hasApplied(User user, Job job) {
        return jobApplicationRepository.findByUserIdAndJobId(user.getId(), job.getId()) != null;
    }

    public List<JobApplications> getUserApplications(Long userId) {
        return jobApplicationRepository.findByUser_Id(userId);
    }

    public void deleteJobApplication(Long applicationId) {
        if (!jobApplicationRepository.existsById(applicationId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found");
        }
        jobApplicationRepository.deleteById(applicationId);
    }
}
