/**
 * Name: Guilherme Mendes
 * Date: 2021-07-25
 */
package com.alumni.batch_processing.coding_demo;

import com.alumni.batch_processing.coding_demo.input.Line;
import com.alumni.batch_processing.coding_demo.output.Host;
import com.alumni.batch_processing.coding_demo.output.HostLineAggregator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class CodingDemoBatchConfiguration {
  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  @Bean
  public FlatFileItemReader<Line> reader() {
    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    tokenizer.setDelimiter("|");
    tokenizer.setNames(new String[]{"host", "scores"});

    return new FlatFileItemReaderBuilder<Line>()
        .name("hostItemReader")
        .resource(new PathResource("input/data.txt"))
        .lineTokenizer(tokenizer)
        .fieldSetMapper(new BeanWrapperFieldSetMapper<Line>() {{
          setTargetType(Line.class);
        }})
        .build();
  }

  @Bean
  public HostItemProcessor processor() {
    return new HostItemProcessor();
  }

  @Bean
  public FlatFileItemWriter<Host> writer() {
    Resource outputResource = new FileSystemResource("output/results.txt");
    FlatFileItemWriter<Host> writer = new FlatFileItemWriter<>();
    writer.setResource(outputResource);
    writer.setAppendAllowed(false);
    writer.setLineAggregator(new HostLineAggregator<>());
    return writer;
  }

  @Bean
  public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
    return jobBuilderFactory.get("importUserJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(step1)
        .end()
        .build();
  }

  @Bean
  public Step step1(FlatFileItemWriter<Host> writer) {
    return stepBuilderFactory.get("step1")
        .<Line, Host> chunk(100)
        .reader(reader())
        .processor(processor())
        .writer(writer)
        .build();
  }
}
