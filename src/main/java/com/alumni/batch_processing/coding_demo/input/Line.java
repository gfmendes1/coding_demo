/**
 * Name: Guilherme Mendes
 * Date: 2021-07-25
 */
package com.alumni.batch_processing.coding_demo.input;

public class Line {
  private String host;
  private String scores;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getScores() {
    return scores;
  }

  public void setScores(String scores) {
    this.scores = scores;
  }

  @Override
  public String toString() {
    return "Line{" +
        "host='" + host + '\'' +
        ", scores='" + scores + '\'' +
        '}';
  }
}
