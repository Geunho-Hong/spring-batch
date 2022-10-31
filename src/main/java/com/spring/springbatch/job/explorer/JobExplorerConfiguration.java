package com.spring.springbatch.job.explorer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JobExplorerConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobRegistry jobRegistry;

    @Bean(name = "explorerJob")
    public Job explorerJob() {
        return jobBuilderFactory.get("explorerJob")
                .start(explorerStep1())
                .next(explorerStep2())
                .build();
    }

    @Bean(name = "explorerStep1")
    public Step explorerStep1(){
        return stepBuilderFactory.get("explorerStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("explorerStep1");
                        return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean(name = "explorerStep2")
    public Step explorerStep2(){
        return stepBuilderFactory.get("explorerStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("explorerStep2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    /*
        생성된 Job 을 자동으로 등록, 추적 및 관리하며 여러 곳에서 job 을 생성한 경우 ApplicationContext 에서 job을 수집해서 사용할 수 있다
        기본 구현체로 map 기반의 MapJobRegistry 클래스를 제공한다
            jobName 을 Key 로 하고 job 을 값으로 하여 매핑한다
        Job 등록
            JobRegistryBeanPostProcessor – BeanPostProcessor 단계에서 bean 초기화 시 자동으로 JobRegistry에 Job을 등록 시켜준다
    */
    @Bean
    public BeanPostProcessor jobRegistryBeanPostProcessor() {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }
}
