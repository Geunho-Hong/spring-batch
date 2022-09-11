package com.spring.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean(name = "jobInitialization")
    public Job jobInitialization() {
        return jobBuilderFactory.get("jobInitialization")
                .start(stepInit1())
                .next(stepInit2())
                .build();
    }

    @Bean(name = "stepInit1")
    public Step stepInit1(){
        return stepBuilderFactory.get("stepInit1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("stepInit1");
                        return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean(name = "stepInit2")
    public Step stepInit2(){
        return stepBuilderFactory.get("stepInit2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("stepInit2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
