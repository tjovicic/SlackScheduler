package com.github.tjovicic.slack_scheduler.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class Job implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String text;

  @Column(nullable = false)
  private LocalDateTime time;

  @Column(nullable = false)
  private String channel;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private JobStatus status;

  public Job() {

  }

  @Override
  public String toString() {
    return String.format("Job[id=%d, text=%s, time=%s, channel=%s, status=%s]",
            this.id, this.text, this.time, this.channel, this.status);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Job job = (Job) o;

    if (!this.text.equals(job.text)) return false;
    if (!this.time.equals(job.time)) return false;
    if (!this.channel.equals(job.channel)) return false;
    return this.status.equals(job.status);
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + this.text.hashCode();
    result = 31 * result + this.time.hashCode();
    result = 31 * result + this.channel.hashCode();
    result = 31 * result + this.status.hashCode();
    return result;
  }
}
