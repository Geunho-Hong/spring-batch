package com.spring.springbatch.job.step;

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
public class LimitAllowConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job limitAllowJob() {
        return this.jobBuilderFactory.get("limitAllowJob")
                .start(limitAllowStep1())
                .next(limitAllowStep2())
                .build();
    }

    @Bean
    public Step limitAllowStep1() {
        return this.stepBuilderFactory.get("limitAllowStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("limitAllowStep1");
                    return RepeatStatus.FINISHED;
                })
                .allowStartIfComplete(true) // 이미 성공한 step 이여도 재실행한다
                .build();
    }

    @Bean
    public Step limitAllowStep2() {
        return this.stepBuilderFactory.get("limitAllowStep2")
                .tasklet((contribution, chunkContext) -> {
                    throw new RuntimeException("limitAllowStep2 was failed");
                })
                .startLimit(3) // step 의 실행횟수를 제한 한다
                .build();
    }

}
