package com.github.tjovicic.slack_scheduler.domain;

import lombok.Data;

@Data
public class Response {

  private String status;

  private String message;

  public Response(final String status, final String message) {
    this.status = status;
    this.message = message;
  }

}
