package com.awesome.tree.service.impl;

import com.awesome.tree.service.UserService;
import com.awesome.tree.dao.UserMapper;
import com.awesome.tree.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @Author tree
 * @Date 2017/7/15 23:04
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User getUserById(Long userId) {
        return userMapper.getUserById(userId);
    }
}
