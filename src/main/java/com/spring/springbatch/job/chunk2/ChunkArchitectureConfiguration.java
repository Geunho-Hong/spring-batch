package com.spring.springbatch.job.chunk2;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;



@RequiredArgsConstructor
@Configuration
public class ChunkArchitectureConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean(name= "chunkArchitectureJob")
    public Job job() {
        return jobBuilderFactory.get("chunkArchitectureJob")
                .start(chunkArichStep1())
                .next(chunkArchiStep2())
                .build();
    }

    @Bean("chunkArichStep1")
    public Step chunkArichStep1() {
        return stepBuilderFactory.get("chunkArichStep1")
                .<String, String>chunk(3)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemReader itemReader() {
        return new CustomItemReader(Arrays.asList(new Customer("user1"), new Customer("user2"), new Customer("user3")));
    }

    @Bean
    public ItemProcessor itemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemWriter itemWriter() {
        return new CustomItemWriter();
    }

    @Bean(name = "chunkArchiStep2")
    public Step chunkArchiStep2() {
        return stepBuilderFactory.get("chunkArchiStep2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("chunkArchiStep2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}