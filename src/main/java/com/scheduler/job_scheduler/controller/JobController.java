package com.scheduler.job_scheduler.controller;

import com.scheduler.job_scheduler.dto.JobRequest;
import com.scheduler.job_scheduler.model.Job;
import com.scheduler.job_scheduler.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody JobRequest request){
        return ResponseEntity.ok(jobService.createJob(request));
    }
}
