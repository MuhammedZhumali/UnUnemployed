package org.hire.job_platform_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyJobRequest {
    private Long userId;
    private Long jobId;
}
