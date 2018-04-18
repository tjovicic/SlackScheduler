package com.github.tjovicic.slack_scheduler.service.impl;

import com.github.tjovicic.slack_scheduler.domain.Job;
import com.github.tjovicic.slack_scheduler.domain.Response;
import com.github.tjovicic.slack_scheduler.repository.JobRepository;
import com.github.tjovicic.slack_scheduler.service.JobService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    return Lists.newArrayList(this.jobRepository.findAll());
  }

  @Override
  public Response save(final Job job) {
    final LocalDateTime minDateTime = LocalDateTime.now().plusMinutes(5);
    if (job.getTime().isBefore(minDateTime)) {
      return new Response("fail", "DateTime before min DateTime: " + minDateTime.toString());
    }

    job.setChannel("test");
    this.jobRepository.save(job);

    return new Response("success", "");
  }

  @Override
  public void delete(final Long id) {
    this.jobRepository.deleteById(id);
  }
}
