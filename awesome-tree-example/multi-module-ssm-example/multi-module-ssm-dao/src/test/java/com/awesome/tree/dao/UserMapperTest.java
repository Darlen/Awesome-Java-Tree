package com.awesome.tree.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Description:
 *
 * @Author tree
 * @Date 2017/7/15 20:15
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void getUserByIdTest(){
        System.err.println(userMapper.getUserById(1L));
    }

}
