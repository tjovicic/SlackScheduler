package com.github.tjovicic.slack_scheduler.service.impl;

import com.github.tjovicic.slack_scheduler.domain.Job;
import com.github.tjovicic.slack_scheduler.repository.JobRepository;
import com.github.tjovicic.slack_scheduler.service.JobService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

  private final JobRepository jobRepository;

  @Autowired
  public JobServiceImpl(final JobRepository jobRepository) {
    this.jobRepository = jobRepository;
  }

  @Override
  public List<Job> getAll() {
    return Lists.newArrayList(jobRepository.findAll());
  }

  @Override
  public Job save(Job job) {
    return jobRepository.save(job);
  }
}
