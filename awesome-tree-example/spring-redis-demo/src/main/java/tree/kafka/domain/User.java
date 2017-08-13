package tree.kafka.domain;

import java.io.Serializable;

/**
 * Description:
 *
 * @Author tree
 * @Date 2017/8/13 21:28
 * @Version 1.0
 */
public class User implements Serializable{
    private static final long serialVersionUID = -6011241820070393952L;

    private String id;
    private String name;
    private String pwd;


    public User() {
    }

    public User(String id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
