package com.scheduler.job_scheduler.service;

import com.scheduler.job_scheduler.model.LeaderLock;
import com.scheduler.job_scheduler.repository.LeaderLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LeaderElectionService {

    private final LeaderLockRepository lockRepository;
    private final String instanceId = UUID.randomUUID().toString();

    private static final String LOCK_NAME = "SCHEDULER_LOCK";

    private static final int LEASE_SECONDS = 15;

    public boolean isLeader() {

        LocalDateTime now = LocalDateTime.now();
        LeaderLock lock = lockRepository.findById(LOCK_NAME).orElse(null);

        if(lock == null){
            return acquireLeadership(now);
        }

        if(lock.getLockedBy().equals(instanceId)){
            lock.setHeartbeatTime(now);
            lockRepository.save(lock);

            return true;
        }

        if(lock.getHeartbeatTime().plusSeconds(LEASE_SECONDS).isBefore(now)) {
            System.out.println("Leader expired. Taking over...");

            lock.setLockedBy(instanceId);
            lock.setHeartbeatTime(now);

            lockRepository.save(lock);

            return true;
        }

        return true;
    }

    private boolean acquireLeadership(LocalDateTime now){
        try {
            LeaderLock lock = new LeaderLock();

            lock.setLockName(LOCK_NAME);
            lock.setLockedBy(instanceId);
            lock.setHeartbeatTime(now);

            lockRepository.save(lock);

            System.out.println("I became leader: " + instanceId);

            return true;
        } catch (Exception e){
            return false;
        }
    }
}
