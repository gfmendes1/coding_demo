/**
 * Name: Guilherme Mendes
 * Date: 2021-07-25
 */
package com.alumni.batch_processing.coding_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  @Override
  public void afterJob(JobExecution jobExecution) {
    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      sortResultFile();
      log.info("!!! JOB FINISHED! Check output folder to see the results.");
    }
  }

  /**
   * I didn't find a scalable way to execute the sort using Spring Batch. I choose UNIX sort command
   * as it is has a very good performance, both in space and time complexity.
   *
   * Maybe, in a real world problem, instead of writing the results in a file, we'd be writing the results in a
   * database and this this would solve the sort part.
   *
   */
  private void sortResultFile() {
    try {
      new ProcessBuilder("./order_results.sh").start();
    } catch (IOException e) {
      log.error("It was not possible to created the sorted file!", e);
    }
  }
}