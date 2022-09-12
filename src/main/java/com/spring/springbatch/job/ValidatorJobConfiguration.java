package com.spring.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ValidatorJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean(name = "simpleBatchJob")
    public Job simpleBatchJob() {
        return jobBuilderFactory.get("simpleBatchJob")
                .start(validatorStep1())
                .next(validatorStep2())
                // 1. customJobParameter Validator
                //.validator(new CustomJobParameterValidator())

                // 2. DefaultJobParameterValue
                .validator(new DefaultJobParametersValidator(new String[]{"name","date"}, new String[]{"count"}))
                .build();
    }

    @Bean(name = "validatorStep1")
    public Step validatorStep1() {
        return stepBuilderFactory.get("validatorStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("validatorStep1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean(name = "validatorStep2")
    public Step validatorStep2() {
        return stepBuilderFactory.get("validatorStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("validatorStep2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
