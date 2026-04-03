package org.hire.job_platform_service.domain.model;

import jakarta.persistence.*;
import lombok.Data;

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

    private String company;
    private String location;
    private double salary;
}
