package com.github.tjovicic.slack_scheduler.service;

import com.github.tjovicic.slack_scheduler.domain.Job;
import com.github.tjovicic.slack_scheduler.domain.Response;

import java.util.List;

public interface JobService {

  List<Job> getAll();

  Response save(Job job);

  void delete(Long id);
}
