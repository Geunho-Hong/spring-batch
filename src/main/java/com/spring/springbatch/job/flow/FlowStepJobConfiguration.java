package com.spring.springbatch.job.flow;

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
public class FlowStepJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowStepJob() {
        return jobBuilderFactory.get("flowStepJob")
                .start(flowStep1006())
                .next(step_1006_2())
                .build();
    }

    @Bean
    public Step flowStep1006() {
        return stepBuilderFactory.get("flowStep1006")
                .flow(flow_1006())
                .build();
    }

    private Flow flow_1006() {

        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow_1006");
        flowBuilder.start(step_1006_1())
                .end();

        return flowBuilder.build();
    }

    @Bean
    public Step step_1006_1() {
        return stepBuilderFactory.get("step_1006_1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("step_1006_1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step_1006_2() {
        return stepBuilderFactory.get("step_1006_2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("step_1006_2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


}
