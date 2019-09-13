package com.techhome.springbatchhome.batch;

import com.techhome.springbatchhome.model.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Processor implements ItemProcessor<User,User> {

    private static final Map<String,String> DEPT_NAME = new HashMap<>();

    public Processor() {
        DEPT_NAME.put("607","DEVELOPMENT");
        DEPT_NAME.put("607","DEVELOPMENT");
        DEPT_NAME.put("608","IT");
        DEPT_NAME.put("608","IT");

    }

    @Override
    public User process(User user) throws Exception {
        user.setDept(DEPT_NAME.get(user.getDept()));
        user.setTime(new Date());
        return user;
    }

}
