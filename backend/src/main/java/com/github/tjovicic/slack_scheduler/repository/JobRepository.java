package com.github.tjovicic.slack_scheduler.repository;

import com.github.tjovicic.slack_scheduler.domain.Job;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Job, String> {
}
