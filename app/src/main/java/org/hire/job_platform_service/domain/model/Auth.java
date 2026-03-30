package org.hire.job_platform_service.domain.model;
l
import jakarta.persistence.*;

@Table(name = "auth")
@Entity
public class Auth {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "password")
    private String password;
}
