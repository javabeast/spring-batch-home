package com.techhome.springbatchhome.batch;

import com.techhome.springbatchhome.model.User;
import com.techhome.springbatchhome.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void write(List<? extends User> list) throws Exception {
        System.out.println("Date getting saved in db");
        userRepository.saveAll(list);
        System.out.println("data saved successfully");
    }
}
