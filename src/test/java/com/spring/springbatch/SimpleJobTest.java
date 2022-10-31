package com.spring.springbatch;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


// 하나의 Job만 Configuration에 넣어야 한다
@SpringBootTest(classes = {TestBatchConfig.class})
public class SimpleJobTest {


    @Test
    public void simpleJobTest() {

        // given
       /* JobParameter jobParameter = new JobParametersBuilder()
                .addString("name", "grayson")
                .addLong("date", new Date().getTime())
                .toJobParameters();

        // when


        Assert.equal */


    }

}
