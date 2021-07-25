package com.alumni.batch_processing.coding_demo; /**
 * Name: Guilherme Mendes
 * Date: 2021-07-25
 */

import com.alumni.batch_processing.coding_demo.input.Line;
import com.alumni.batch_processing.coding_demo.output.Host;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class HostItemProcessorTest {

  @Test
  public void givenFineInput_whenJobExecuted_thenConsiderAllValues() throws Exception {

    Line line = new Line();
    line.setHost("n1,34234234,235235");
    line.setScores("99.0,1.0,50");

    HostItemProcessor processor = new HostItemProcessor();
    Host host = processor.process(line);

    assertEquals("n1", host.getName());
    assertEquals(Double.valueOf("99"), host.getMax());
    assertEquals(Double.valueOf("1"), host.getMin());
    assertEquals(Double.valueOf("50"), host.getAverage());
  }

  @Test
  public void givenSomeIncorrectScores_whenJobExecuted_thenAverageIgnoresIncorrectScores() throws Exception {

    Line line = new Line();
    line.setHost("n1,34234234,235235");
    line.setScores("80.0,10.0,None,null");

    HostItemProcessor processor = new HostItemProcessor();
    Host host = processor.process(line);

    assertEquals("n1", host.getName());
    assertEquals(Double.valueOf("80"), host.getMax());
    assertEquals(Double.valueOf("10"), host.getMin());
    assertEquals(Double.valueOf("45"), host.getAverage());
  }

  @Test( expected = NoSuchElementException.class)
  public void givenNoScores_whenJobExecuted_thenThrowsException() throws Exception {

    Line line = new Line();
    line.setHost("n1,34234234,235235");
    line.setScores("None,None");

    HostItemProcessor processor = new HostItemProcessor();
    processor.process(line);
  }


}
