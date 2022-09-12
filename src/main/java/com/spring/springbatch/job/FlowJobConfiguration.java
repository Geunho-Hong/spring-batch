package com.spring.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FlowJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean(name = "flowJob")
    public Job flowJob() {
        return jobBuilderFactory.get("flowJob")
                .start(flow())
                .end()
                .build();
    }

    @Bean(name = "flow")
    public Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<Flow>("flow");
        
        flowBuilder.start(flowStep1())
                .next(flowStep2())
                .end();

        return flowBuilder.build();
    }

    @Bean(name = "flowStep1")
    public Step flowStep1() {
        return stepBuilderFactory.get("flowStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("flowStep1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean(name = "flowStep2")
    public Step flowStep2() {
        return stepBuilderFactory.get("flowStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("flowStep2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
