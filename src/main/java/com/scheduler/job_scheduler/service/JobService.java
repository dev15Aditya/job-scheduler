package com.scheduler.job_scheduler.service;

import com.scheduler.job_scheduler.dto.JobRequest;
import com.scheduler.job_scheduler.model.Job;
import com.scheduler.job_scheduler.model.JobStatus;
import com.scheduler.job_scheduler.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    public Job createJob(JobRequest request){
        Job job = Job.builder()
                .jobName(request.getJobName())
                .payload(request.getPayload())
                .scheduleTime(request.getScheduleTime())
                .status(JobStatus.CREATED)
                .build();

        return jobRepository.save(job);
    }
}
