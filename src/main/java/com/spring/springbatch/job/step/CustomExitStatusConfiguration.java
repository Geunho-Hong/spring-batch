package com.spring.springbatch.job.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CustomExitStatusConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /**
     * 구문을 만족하지 못했기 때문에 JobExecution을 실패로 간주
     * @return
     */

    @Bean
    public Job customExitStatusJob() {
        return this.jobBuilderFactory.get("customExitStatusJob")
                .start(exitStatusStep1())
                    .on("FAILED")
                    .to(exitStatusStep2())
                    .on("PASS")
                    .stop()
                .end()
                .build();
    }

    @Bean
    public Step exitStatusStep1() {
        return stepBuilderFactory.get("exitStatusStep1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("exitStatusStep1");
                    contribution.getStepExecution().setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step exitStatusStep2() {
        return stepBuilderFactory.get("exitStatusStep2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("exitStatusStep2");
                    return RepeatStatus.FINISHED;
                })
                .listener(new PassCheckingListener())
                .build();
    }
}
