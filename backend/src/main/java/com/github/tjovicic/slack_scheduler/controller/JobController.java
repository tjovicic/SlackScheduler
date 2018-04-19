package com.github.tjovicic.slack_scheduler.controller;

import com.github.tjovicic.slack_scheduler.domain.Job;
import com.github.tjovicic.slack_scheduler.domain.Response;
import com.github.tjovicic.slack_scheduler.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {

  private final JobService jobService;

  @Autowired
  public JobController(final JobService jobService) {
    this.jobService = jobService;
  }

  @GetMapping("/api/jobs")
  public List<Job> getAll() {
    return this.jobService.getAll();
  }

  @PostMapping("/api/jobs")
  public Response create(@RequestBody final Job job) {
    return this.jobService.save(job);
  }

  @DeleteMapping("/api/jobs/{id}")
  public void delete(@PathVariable final Long id) {
    this.jobService.delete(id);
  }
}
