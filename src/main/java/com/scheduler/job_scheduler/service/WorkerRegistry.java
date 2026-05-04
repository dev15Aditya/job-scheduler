package com.scheduler.job_scheduler.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class WorkerRegistry {

    private final List<String> workers = new CopyOnWriteArrayList<>();
    private int index = 0;

    public WorkerRegistry(){
        // manually registering workers
        workers.add("http://localhost:8080/worker");
        workers.add("http://localhost:8081/worker"); // simulate another worker
    }

    public String getNextWorker() {
        if(workers.isEmpty()) {
            throw new RuntimeException("No workers available");
        }

        String worker = workers.get(index);
        index = (index + 1) % workers.size();
        return worker;
    }
}
