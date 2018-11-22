package com.zh.springboot.controller;

import com.zh.springboot.entity.User;
import com.zh.springboot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = "/user")
public class UserController {
    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/sayYes")
    public String sayYes() {
        return "Yes, hahahah";
    }

    @RequestMapping(path = "/sayNo")
    public String sayHello2() {
        return "No, No, No.";
    }

    @RequestMapping(path = "/saveUser")
    public User saveUser(@RequestParam String name) {
        User user = new User();
        user.setName(name);

        boolean saveResult = userRepository.saveUser(user);

        if(saveResult) {
            log.info("用户对象" + user.toString() + "保存成功。");
        } else {
            log.error("用户对象" + user.toString() + "保存失败！！！");
        }

        return user;
    }

}
