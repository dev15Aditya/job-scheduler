package com.scheduler.job_scheduler.controller;

import com.scheduler.job_scheduler.model.Job;
import com.scheduler.job_scheduler.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/worker")
@RequiredArgsConstructor
public class WorkController {
    private final WorkerService workerService;

    @PostMapping("/execute")
    public ResponseEntity<String> executeJob(@RequestBody Job job) {
        workerService.execute(job);
        return ResponseEntity.ok("Job received");
    }
}
