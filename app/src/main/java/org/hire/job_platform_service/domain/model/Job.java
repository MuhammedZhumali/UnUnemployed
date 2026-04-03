package org.hire.job_platform_service.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "jobs")
@Data
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobApplications> applications;

    private String company;
    private String location;
    private double salary;
}
