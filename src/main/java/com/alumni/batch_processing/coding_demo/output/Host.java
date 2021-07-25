/**
 * Name: Guilherme Mendes
 * Date: 2021-07-25
 */
package com.alumni.batch_processing.coding_demo.output;

public class Host {

  private String name;
  private Double average;
  private Double max;
  private Double min;

  public Host(String name, Double average, Double max, Double min) {
    this.name = name;
    this.average = average;
    this.max = max;
    this.min = min;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getAverage() {
    return average;
  }

  public void setAverage(Double average) {
    this.average = average;
  }

  public Double getMax() {
    return max;
  }

  public void setMax(Double max) {
    this.max = max;
  }

  public Double getMin() {
    return min;
  }

  public void setMin(Double min) {
    this.min = min;
  }

  @Override
  public String toString() {
    return "Host{" +
        "name='" + name + '\'' +
        ", average=" + average +
        ", max=" + max +
        ", min=" + min +
        '}';
  }
}
