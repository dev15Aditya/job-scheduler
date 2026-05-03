package com.scheduler.job_scheduler.service;

import com.scheduler.job_scheduler.model.Job;
import com.scheduler.job_scheduler.model.JobStatus;
import com.scheduler.job_scheduler.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final JobRepository jobRepository;

    public void execute(Job job){
        try {
            job.setStatus(JobStatus.RUNNING);
            jobRepository.save(job);

            System.out.println("Worker executing: " + job.getJobName());

            Thread.sleep(3000);

            job.setStatus(JobStatus.SUCCESS);
        } catch (Exception e){
            job.setStatus(JobStatus.FAILED);
        }

        jobRepository.save(job);
    }
}
