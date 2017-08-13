package com.awesome.tree.service;

//import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSON;
import com.awesome.tree.dao.UserMapper;
import com.awesome.tree.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Description:
 *
 * @Author tree
 * @Date 2017/7/15 23:07
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-servive.xml","classpath:spring/spring.xml"})
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void getUserById(){
        User user = userService.getUserById(1L);
        System.out.println(JSON.toJSON(user));
    }

    public static void main(String[] args) {
        System.out.println(1);
    }
}
