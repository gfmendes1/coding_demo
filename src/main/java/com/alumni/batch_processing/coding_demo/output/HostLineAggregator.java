/**
 * Name: Guilherme Mendes
 * Date: 2021-07-25
 */
package com.alumni.batch_processing.coding_demo.output;

import org.springframework.batch.item.file.transform.ExtractorLineAggregator;

public class HostLineAggregator<Host> extends ExtractorLineAggregator {

  @Override
  protected String doAggregate(Object[] objects) {
    com.alumni.batch_processing.coding_demo.output.Host host = (com.alumni.batch_processing.coding_demo.output.Host) objects[0];
    return host.getName() + ":" + "Average:" + host.getAverage() + " Max:" + host.getMax() + " Min:" + host.getMin();
  }
}
