package tree.kafka;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tree.kafka.dao.UserDao;
import tree.kafka.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author tree
 * @Date 2017/8/13 21:49
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class RedisTest {

    @Autowired
    private UserDao userDao;

    /**
     * 新增
     * <br>------------------------------<br>
     */
    @Test
    public void addNx() {
        User user = new User();
        user.setId("treeId");
        user.setName("tree");
        boolean result = userDao.addNx(user);
        Assert.assertTrue(result);
    }

    /**
     * 批量新增 普通方式
     * <br>------------------------------<br>
     */
    @Test
    public void testAddUsers1() {
        List<User> list = new ArrayList<User>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId("user" + i);
            user.setName("tree" + i);
            list.add(user);
        }
        long begin = System.currentTimeMillis();
        for (User user : list) {
            userDao.addNx(user);
        }
        System.out.println(System.currentTimeMillis() -  begin);
    }

    /**
     * 批量新增 pipeline方式
     * <br>------------------------------<br>
     */
    @Test
    public void batchAddNx() {
        List<User> list = new ArrayList<User>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId("user" + i);
            user.setName("tree" + i);
            list.add(user);
        }
        long begin = System.currentTimeMillis();
        boolean result = userDao.batchAddNx(list);
        System.out.println(System.currentTimeMillis() - begin);
        Assert.assertTrue(result);
    }

    /**
     * 修改
     * <br>------------------------------<br>
     */
    @Test
    public void testUpdate() {
        User user = new User();
        user.setId("user1");
        user.setName("new_password");
        boolean result = userDao.update(user);
        Assert.assertTrue(result);
    }

    /**
     * 通过key删除单个
     * <br>------------------------------<br>
     */
    @Test
    public void testDelete() {
        String key = "treeId";
        userDao.delete(key);
    }

    /**
     * 批量删除
     * <br>------------------------------<br>
     */
    @Test
    public void testDeletes() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("user" + i);
        }
        userDao.delete(list);
    }

    /**
     * 获取
     * <br>------------------------------<br>
     */
    @Test
    public void testGetUser() {
        String id = "treeId";
        User user = userDao.get(id);
        System.err.println(JSON.toJSONString(user));
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getName(), "tree");
    }

    /**
     * 设置userDao
     * @param userDao the userDao to set
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
