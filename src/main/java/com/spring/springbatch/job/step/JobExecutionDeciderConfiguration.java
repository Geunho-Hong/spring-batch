package com.spring.springbatch.job.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobExecutionDeciderConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job customDeciderJob() {
        return this.jobBuilderFactory.get("customDeciderJob")
                .incrementer(new RunIdIncrementer())
                .start(deciderStep())
                .next(decider())
                .from(decider()).on("ODD").to(oddStep())
                .from(decider()).on("EVEN").to(evenStep())
                .end()
                .build();
    }

    /**
     * JobExecutionDecider
     * Transition 처리를 위한 전용 클래스
     * Step과 Transition 역할을 명확히 분리해서 사용할 수 있다
     */

    public JobExecutionDecider decider() {
        return new CustomDecider();
    }

    @Bean
    public Step deciderStep() {
        return stepBuilderFactory.get("deciderStep")
                .tasklet((contribution, chunkContext) -> {
            System.out.println("deciderStep ");
            return RepeatStatus.FINISHED;
        })
        .build();
    }


    @Bean
    public Step oddStep() {
        return stepBuilderFactory.get("oddStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("oddStep");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step evenStep() {
        return stepBuilderFactory.get("evenStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("evenStep");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
