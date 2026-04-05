package org.hire.job_platform_service.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.hire.job_platform_service.domain.model.JobApplications;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplications, Long> {
    @Query("SELECT ja FROM JobApplications ja WHERE ja.user.id = :userId AND ja.job.id = :jobId")
    JobApplications findByUserIdAndJobId(Long userId, Long jobId);

    List<JobApplications> findByUser_Id(Long userId);
}
