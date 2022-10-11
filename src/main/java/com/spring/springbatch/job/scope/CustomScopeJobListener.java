package com.spring.springbatch.job.scope;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;


public class CustomScopeJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().put("name","graysonJob");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

    }
}
