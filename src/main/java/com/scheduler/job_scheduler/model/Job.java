package com.scheduler.job_scheduler.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobName;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private LocalDateTime scheduleTime;

    @Enumerated(EnumType.STRING)
    private JobStatus status;
}
