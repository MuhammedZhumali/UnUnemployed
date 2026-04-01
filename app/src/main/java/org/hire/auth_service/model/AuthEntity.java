package org.hire.auth_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import lombok.*;

@Table(name = "auth")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthEntity {
    
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "password")
    private String password;
}
