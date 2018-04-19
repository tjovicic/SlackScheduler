package com.github.tjovicic.slack_scheduler.service.impl;

import com.github.tjovicic.slack_scheduler.domain.Job;
import com.github.tjovicic.slack_scheduler.domain.JobStatus;
import com.github.tjovicic.slack_scheduler.domain.Response;
import com.github.tjovicic.slack_scheduler.repository.JobRepository;
import com.github.tjovicic.slack_scheduler.service.JobService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

  private final static Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);

  private final JobRepository jobRepository;

  @Value("${slack.channel:/}")
  private String channel;

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
    if (job.getId() != null) {
      return updateJob(job);
    } else {
      return saveNewJob(job);
    }
  }

  private Response saveNewJob(final Job job) {
    if (isTimeValid(job.getTime())) {
      return new Response("fail", "Time must be at least 1 minute in the future");
    }

    job.setChannel(this.channel);
    this.jobRepository.save(job);
    LOGGER.info("Job saved : " + job.toString());

    return new Response("success", "");
  }

  private Response updateJob(final Job job) {
    this.jobRepository.save(job);
    LOGGER.info("Job updated : " + job.toString());

    return new Response("success", "");
  }

  @Override
  public void delete(final Long id) {
    this.jobRepository.deleteById(id);
  }

  @Override
  public List<Job> getForTime(final LocalDateTime time) {
    return this.jobRepository.findAllByTimeIsLessThanEqualAndStatusEquals(time, JobStatus.PENDING);
  }

  private boolean isTimeValid(final LocalDateTime time) {
    final LocalDateTime minDateTime = LocalDateTime.now().plusMinutes(1);
    return time.isBefore(minDateTime);
  }
}
