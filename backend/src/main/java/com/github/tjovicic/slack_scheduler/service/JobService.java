package com.github.tjovicic.slack_scheduler.service;

import com.github.tjovicic.slack_scheduler.domain.Job;

import java.util.List;

public interface JobService {

  List<Job> getAll();

  Job save(Job job);
}
