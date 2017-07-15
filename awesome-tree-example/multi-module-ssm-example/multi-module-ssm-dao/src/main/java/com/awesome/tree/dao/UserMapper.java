package com.awesome.tree.dao;

import com.awesome.tree.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Description: 用户模块
 *
 * @Author tree
 * @Date 2017/7/15 18:23
 * @Version 1.0
 */
@Repository
public interface UserMapper {

    User getUserById(@Param("id") Long userId);

}
