package com.spring.springbatch.job.increment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class IncrementerConfigurationJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean(name = "incrementJob1")
    public Job incrementJob1 () {
        return jobBuilderFactory.get("incrementJob1")
                .start(incrementStep1())
                .next(incrementStep2())
                .incrementer(new CustomJobParameterIncrementer())
                .build();
    }

    @Bean(name = "incrementStep1")
    public Step incrementStep1() {
        return stepBuilderFactory.get("incrementStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("incrementStep1 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean(name = "incrementStep2")
    public Step incrementStep2() {
        return stepBuilderFactory.get("incrementStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("incrementStep2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
