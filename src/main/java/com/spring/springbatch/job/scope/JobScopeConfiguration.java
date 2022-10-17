package com.spring.springbatch.job.scope;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * @JobScope, @StepScope
 * Job 과 Step 의 빈 생성과 실행에 관여하는 스코프
 * 프록시 모드를 기본값으로 하는 스코프 - @Scope(value = "job", proxyMode = ScopedProxyMode.TARGET_CLASS)
 * 해당 스코프가 선언되면 빈이 생성이 어플리케이션 구동시점이 아닌 빈의 실행시점에 이루어진다
 * @Values를 주입해서 빈의 실행 시점에 값을 참조할 수 있으며 일종의 Lazy Binding 이 가능해 진다
 * @Value("#{jobParameters[파라미터명]}"), @Value("#{jobExecutionContext[파라미터명]”}), @Value("#{stepExecutionContext[파라미터명]”})
 * @Values 를 사용할 경우 빈 선언문에 @JobScope , @StepScope 를 정의하지 않으면 오류를 발생하므로 반드시 선언해야 함
 * 프록시 모드로 빈이 선언되기 때문에 어플리케이션 구동시점에는 빈의 프록시 객체가 생성되어 실행 시점에 실제 빈을 호출해 준다
 * 병렬처리 시 각 스레드 마다 생성된 스코프 빈이 할당되기 때문에 스레드에 안전하게 실행이 가능하다
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobScopeConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean(name = "scopeJob")
    public Job scopeJob() {
        return jobBuilderFactory.get("scopeJob")
                .start(scopeStep1(null))
                .next(scopeStep2())
                .listener(new CustomScopeJobListener())
                .build();
    }

    /**
     * @JobScope 는 Step 선언문에 정의한다
     * @Value : jobParameter, jobExecutionContext만 사용 가능하다
     */

    @Bean
    @JobScope
    public Step scopeStep1(@Value("#{jobParameters['message']}") String message) {
        log.info("message = " + message);

        return stepBuilderFactory.get("scopeStep1")
                .allowStartIfComplete(true)
                .tasklet(scopeTasklet(null))
                .build();
    }

    @Bean
    @JobScope
    public Step scopeStep2() {
        return stepBuilderFactory.get("scopeStep2")
                .allowStartIfComplete(true)
                .tasklet(scopeTasklet2(null))
                .listener(new CustomScopeStepListener())
                .build();
    }

    /**
     * @StepScope는 Tasklet이나 ItemReader, ItemWriter, ItemProcessor 선언문에 정의한다
     * @Value : jobParameter, jobExecutionContext, stepExecutionContext에 사용가능
     */

    @Bean
    @StepScope
    public Tasklet scopeTasklet(@Value("#{jobExecutionContext['name']}") String name) {
        System.out.println("name = " + name);
        return (stepContribution, chunkContext) -> {
            System.out.println("scopeTasklet 1");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    @StepScope
    public Tasklet scopeTasklet2(@Value("#{stepExecutionContext['name2']}") String name2) {
        System.out.println("name2 = " + name2);
        return (stepContribution, chunkContext) -> {
            System.out.println("scopeTasklet 2");
            return RepeatStatus.FINISHED;
        };
    }
}


