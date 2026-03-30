package org.hire.job_platform_service.domain.model;

import lombok.*;
import jakarta.persistence.*;

@Table(name = "users")
@Data
@Entity
public class User{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}