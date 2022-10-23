package com.spring.springbatch.job.chunk2;

import org.springframework.batch.item.ItemReader;
import java.util.ArrayList;
import java.util.List;

class CustomItemReader implements ItemReader<Customer> {

    private List<Customer> list;

    public CustomItemReader(List<Customer> list) {
        this.list = new ArrayList<>(list);
    }

    @Override
    public Customer read() {
        if (!list.isEmpty()) {
            return list.remove(0);
        }
        return null;
    }
}