package com.spring.springbatch.job.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FlowJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean(name = "flowJob")
    public Job flowJob(){
        return jobBuilderFactory.get("flowJob")
                .start(flowStep1())
                .on("COMPLETED").to(flowStep3())
                .from(flowStep1())
                .on("FAILED").to(flowStep2())
                .end()
                .build();
    }

    @Bean(name = "flowStep1")
    public Step flowStep1() {
        return stepBuilderFactory.get("flowStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info("flowStep1 has executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean(name = "flowStep2")
    public Step flowStep2() {
        return stepBuilderFactory.get("flowStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("flowStep2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean(name = "flowStep3")
    public Step flowStep3() {
        return stepBuilderFactory.get("flowStep3")
                .tasklet((contribution, chunkContext) -> {
                    log.info("flowStep3 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
