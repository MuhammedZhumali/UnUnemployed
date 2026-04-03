package org.hire.job_platform_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDto {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private String company;
    private String location;
    private double salary;
}
