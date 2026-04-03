package org.hire.job_platform_service.domain.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "job_applications")
@Data
public class JobApplications {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne 
    @JoinColumn(name = "job_id")
    private Job job;
}
