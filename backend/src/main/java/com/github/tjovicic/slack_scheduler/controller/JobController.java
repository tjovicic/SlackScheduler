package com.github.tjovicic.slack_scheduler.controller;

import com.github.tjovicic.slack_scheduler.domain.Job;
import com.github.tjovicic.slack_scheduler.service.JobService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobController {

  private final JobService jobService;

  @Autowired
  public JobController(final JobService jobService) {
    this.jobService = jobService;
  }

  @GetMapping("/api/jobs")
  public List<Job> getJobs() {
    return this.jobService.getAll();
  }

  @PostMapping("/api/jobs")
  public Job createJob(@RequestBody final Job job) {
    return this.jobService.save(job);
  }
}
