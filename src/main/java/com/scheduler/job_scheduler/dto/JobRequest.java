package com.scheduler.job_scheduler.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobRequest {
    private String jobName;
    private String payload;
    private LocalDateTime scheduleTime;
}
