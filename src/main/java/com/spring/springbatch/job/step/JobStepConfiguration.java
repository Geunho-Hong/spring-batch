package com.spring.springbatch.job.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.job.DefaultJobParametersExtractor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * childJob이 실패하면 ParentJob도 실패한다
 * -> ParentJob은 childJob을 내포하고 있기 때문이다
 */

/**
 * childJob이 성공하면 childJob은 성공!
 * ParentJob만 실패!
 */

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JobStepConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job parentJob() {
        return this.jobBuilderFactory.get("parentJob")
                .start(jobStep(null))
                .next(jobTaskStep2())
                .build();
    }

    @Bean
    public Step jobStep(JobLauncher jobLauncher) {
        return stepBuilderFactory.get("jobStep")
                .job(childJob())
                .launcher(jobLauncher)
                .parametersExtractor(jobParametersExtractor())
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        stepExecution.getExecutionContext().putString("name","grayson");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        return null;
                    }
                })
                .build();
    }

    private DefaultJobParametersExtractor jobParametersExtractor() {
        DefaultJobParametersExtractor extractor = new DefaultJobParametersExtractor();
        extractor.setKeys(new String[]{"name"});
        return extractor;
    }

    @Bean
    public Step jobTaskStep1() {
        return stepBuilderFactory.get("jobTaskStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("jobTaskStep1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step jobTaskStep2() {
        return stepBuilderFactory.get("jobTaskStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("jobTaskStep2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Job childJob() {
        return jobBuilderFactory.get("childJob")
                .start(jobTaskStep1())
                .build();
    }

}
