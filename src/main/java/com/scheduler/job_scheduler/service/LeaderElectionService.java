package com.scheduler.job_scheduler.service;

import com.scheduler.job_scheduler.model.LeaderLock;
import com.scheduler.job_scheduler.repository.LeaderLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LeaderElectionService {

    private final LeaderLockRepository lockRepository;
    private final String instanceId = UUID.randomUUID().toString();

    public boolean isLeader() {

        String lockName = "SCHEDULER_LOCK";

        if(lockRepository.existsById(lockName)) {
            LeaderLock lock = lockRepository.findById(lockName).get();

            return lock.getLockedBy().equals(instanceId);
        }

        try {
            LeaderLock lock = new LeaderLock();
            lock.setLockName(lockName);
            lock.setLockedBy(instanceId);

            lockRepository.save(lock);

            System.out.println("I became leader: " + instanceId);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
