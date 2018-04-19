package com.github.tjovicic.slack_scheduler.repository;

import com.github.tjovicic.slack_scheduler.domain.Job;
import com.github.tjovicic.slack_scheduler.domain.JobStatus;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface JobRepository extends CrudRepository<Job, Long> {

  List<Job> findAllByTimeIsLessThanEqualAndStatusEquals(LocalDateTime time, JobStatus status);

}
