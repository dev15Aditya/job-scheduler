package com.scheduler.job_scheduler.repository;

import com.scheduler.job_scheduler.model.Job;
import com.scheduler.job_scheduler.model.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByStatusAndScheduleTimeBefore(
            JobStatus status,
            LocalDateTime time
    );
}
