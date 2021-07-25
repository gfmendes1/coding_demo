/**
 * Name: Guilherme Mendes
 * Date: 2021-07-25
 */
package com.alumni.batch_processing.coding_demo;

import com.alumni.batch_processing.coding_demo.input.Line;
import com.alumni.batch_processing.coding_demo.output.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import java.util.stream.Collectors;

public class HostItemProcessor implements ItemProcessor<Line, Host> {

  private static final Logger log = LoggerFactory.getLogger(HostItemProcessor.class);

  @Override
  public Host process(final Line line) throws Exception {
    final String hostName = line.getHost().split(",")[0];
    final String[] scoresTmp = line.getScores().split(",");

    List<Double> scores = Arrays.stream(scoresTmp)
        .filter(s -> !s.matches("[^[0-9]+.[0-9]+]+$"))
        .map(Double::valueOf)
        .collect(Collectors.toList());

    Double min = scores.stream().mapToDouble(n -> n).min().orElseThrow(NoSuchElementException::new);
    Double max = scores.stream().mapToDouble(n -> n).max().orElseThrow(NoSuchElementException::new);
    Double average = scores.stream().mapToDouble(n -> n).average().orElseThrow(NoSuchElementException::new);

    final Host transformedHost = new Host(hostName, average, max, min);

    log.info("Converting (" + line + ") into (" + transformedHost + ")");

    return transformedHost;
  }

}