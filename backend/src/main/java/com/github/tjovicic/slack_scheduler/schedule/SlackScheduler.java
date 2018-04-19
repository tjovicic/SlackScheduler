package com.github.tjovicic.slack_scheduler.schedule;

import com.github.tjovicic.slack_scheduler.domain.Job;
import com.github.tjovicic.slack_scheduler.domain.JobStatus;
import com.github.tjovicic.slack_scheduler.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class SlackScheduler {

  private final static Logger LOGGER = LoggerFactory.getLogger(SlackScheduler.class);

  private final JobService jobService;

  @Autowired
  public SlackScheduler(final JobService jobService) {
    this.jobService = jobService;
  }

  @Value("${slack.webhook.url:/}")
  private String webhookUrl;

  @Scheduled(cron = "0 * * * * *")
  public void sendMessages() {
    LOGGER.info("[" + LocalDateTime.now().toString() + "]" + " Sending messages started");

    final List<Job> jobs = this.jobService.getForTime(LocalDateTime.now());

    for (final Job job : jobs) {
      LOGGER.info("Sending for job: " + job.toString());

      try {
        sendMessageToChannel(job.getText(), job.getChannel());
        job.setStatus(JobStatus.SENT);
      } catch (final HttpClientErrorException e) {
        LOGGER.error("Error while sending message: " + Arrays.toString(e.getStackTrace()));
        job.setStatus(JobStatus.ERROR);
      }

      this.jobService.save(job);
    }

    LOGGER.info("Sending messages ended");
  }

  private void sendMessageToChannel(final String text, final String channel) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    final String payload = String.format("{\"text\": \"%s\", \"channel\": \"%s\" }", text, channel);

    final HttpEntity<String> request = new HttpEntity<>(payload, headers);
    new RestTemplate().postForObject(this.webhookUrl, request, String.class);
  }
}
