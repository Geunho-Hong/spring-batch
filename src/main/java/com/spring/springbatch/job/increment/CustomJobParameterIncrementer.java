package com.spring.springbatch.job.increment;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 동일한 JobParameter를 가진 Job을 여러번 실행하는 구성 클래스
 * -> Increment를 사용하면 매번 배치 메타 테이블을 초기화 하지 않아도 된다
 */
public class CustomJobParameterIncrementer implements JobParametersIncrementer {

    static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-hhmmss");

    @Override
    public JobParameters getNext(JobParameters parameters) {

        String id = format.format(new Date());
        return new JobParametersBuilder().addString("run.id", id).toJobParameters();

    }
}
