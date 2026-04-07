package org.hire.job_platform_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto{
    private Long id;
    private String name;
    private String email;
}