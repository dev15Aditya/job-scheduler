package com.scheduler.job_scheduler.scheduler;

import com.scheduler.job_scheduler.model.Job;
import com.scheduler.job_scheduler.model.JobStatus;
import com.scheduler.job_scheduler.repository.JobRepository;
import com.scheduler.job_scheduler.service.LeaderElectionService;
import com.scheduler.job_scheduler.service.WorkerRegistry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class JobScheduler {
    private final JobRepository jobRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private final WorkerRegistry workerRegistry;

    private final LeaderElectionService leaderElectionService;

    // Thread pool for parallel execution
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void scheduleJob() {
        if(!leaderElectionService.isLeader()) {
            return;
        }
        
        List<Job> jobs = jobRepository.findByStatusAndScheduleTimeBefore(
                JobStatus.CREATED,
                LocalDateTime.now()
        );

        for(Job job: jobs){
            // mark as scheduled to avoid duplicate pickup
            job.setStatus(JobStatus.SCHEDULED);
            jobRepository.save(job);

            try {
                String workerUrl = workerRegistry.getNextWorker();

                restTemplate.postForObject(
                        workerUrl + "/execute",
                        job,
                        String.class
                );
            } catch (Exception e) {
                job.setStatus(JobStatus.FAILED);
                jobRepository.save(job);
            }
        }
    }

    private void executeJob(Job job){
        try {
            job.setStatus(JobStatus.RUNNING);
            jobRepository.save(job);

            System.out.println("Executing job: " + job.getJobName());

            // simulate work
            Thread.sleep(3000);

            job.setStatus(JobStatus.SUCCESS);
        } catch (Exception e){
            job.setStatus(JobStatus.FAILED);
        }

        jobRepository.save(job);
    }
}
