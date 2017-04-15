package com.yewei.app.server.service;

import com.yewei.app.server.pojo.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by lenovo on 2017/4/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
//
//    @Test
//    public void addUser() {
//        System.out.println(userService.addUser("18059006498", "123456", "127.0.0.1"));
//        System.out.println(userService.addUser("18059006499", "123456", "127.0.0.2"));
//    }
//
//    @Test
//    public void getAuthenticatedUser() {
//        System.out.println(userService.getAuthenticatedUser("18059006498", "123456"));
//        System.out.println(userService.getAuthenticatedUser(4L, "123456"));
//    }
//
//    @Test
//    public void updatePassword() {
//        System.out.println(userService.modifyPassword(4L, "456789"));
//    }

    @Test
    public void getUser() {
        User user2 = userService.getUser("18059006499");
        System.out.println("user2:" + user2);

    }

}
