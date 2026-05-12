package com.scheduler.job_scheduler.repository;

import com.scheduler.job_scheduler.model.LeaderLock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderLockRepository extends JpaRepository<LeaderLock, String> {
}
