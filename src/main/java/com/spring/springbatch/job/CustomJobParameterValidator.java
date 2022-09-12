package com.spring.springbatch.job;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

/**
 * Custom 하게 정의한 JobParameterValidator
 */
public class CustomJobParameterValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        if(parameters.getString("name") == null) {
            throw new JobParametersInvalidException("name parameter is not found");
        }
    }
}
