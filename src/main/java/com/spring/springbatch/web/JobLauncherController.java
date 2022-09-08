package com.spring.springbatch.web;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class JobLauncherController {

    private final Job job;
    private final JobLauncher jobLauncher;
    private final BasicBatchConfigurer basicBatchConfigurer;

    public JobLauncherController(Job job, JobLauncher jobLauncher, BasicBatchConfigurer basicBatchConfigurer) {
        this.job = job;
        this.jobLauncher = jobLauncher;
        this.basicBatchConfigurer = basicBatchConfigurer;
    }

    @PostMapping("/batch")
    public String launch(@RequestBody Member member) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameter = new JobParametersBuilder()
                .addString("memberId", member.getMemberId())
                .addDate("date", new Date())
                .toJobParameters();

        jobLauncher.run(job,jobParameter);

        return "batch completed";
    }

    @PostMapping("/async-batch")
    public String asyncLaunch(@RequestBody Member member) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameter = new JobParametersBuilder()
                .addString("memberId", member.getMemberId())
                .addDate("date", new Date())
                .toJobParameters();

        SimpleJobLauncher jobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher();
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.run(job,jobParameter);

        return "batch completed";
    }

}
