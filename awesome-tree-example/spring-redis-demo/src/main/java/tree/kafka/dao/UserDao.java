package tree.kafka.dao;

import tree.kafka.domain.User;

import java.util.List;

/**
 * Description:
 *
 * @Author tree
 * @Date 2017/8/13 21:45
 * @Version 1.0
 */
public interface UserDao {
    /**
     * 新增
     * <br>------------------------------<br>
     * @param user
     * @return
     */
    boolean addNx(User user);

    /**
     * 批量新增 使用pipeline方式
     * <br>------------------------------<br>
     * @param list
     * @return
     */
    boolean batchAddNx(List<User> list);

    /**
     * 删除
     * <br>------------------------------<br>
     * @param key
     */
    void delete(String key);

    /**
     * 删除多个
     * <br>------------------------------<br>
     * @param keys
     */
    void delete(List<String> keys);

    /**
     * 修改
     * <br>------------------------------<br>
     * @param user
     * @return
     */
    boolean update(User user);

    /**
     * 通过key获取
     * <br>------------------------------<br>
     * @param keyId
     * @return
     */
    User get(String keyId);
}
