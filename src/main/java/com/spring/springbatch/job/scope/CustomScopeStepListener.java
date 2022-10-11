package com.spring.springbatch.job.scope;

import org.springframework.batch.core.*;


public class CustomScopeStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        stepExecution.getExecutionContext().put("name2","graysonStep2");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
