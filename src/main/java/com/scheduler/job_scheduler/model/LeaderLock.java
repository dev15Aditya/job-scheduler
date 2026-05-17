package com.scheduler.job_scheduler.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "scheduler_lock")
@Getter
@Setter
public class LeaderLock {

    @Id
    private String lockName;

    private String lockedBy;

    private LocalDateTime heartbeatTime;
}
