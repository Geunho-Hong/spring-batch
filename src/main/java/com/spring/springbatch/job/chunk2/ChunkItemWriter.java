package com.spring.springbatch.job.chunk2;

import org.springframework.batch.item.ItemWriter;
import java.util.List;

class CustomItemWriter implements ItemWriter<Customer> {

    @Override
    public void write(List<? extends Customer> items) throws Exception {
        items.forEach(item -> System.out.println(item));
    }
}