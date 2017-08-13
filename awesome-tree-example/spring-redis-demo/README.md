## SPRING REDIS DEMO教程

### 1. jar 包管理(除了spring的相关包，还需要以下2个)
```
       <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-redis</artifactId>
            <version>1.8.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
            <version>2.9.0</version>
        </dependency>
```
### 2. 相关Spring配置
```
    <!-- redis pool 相关配置 -->
	<bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig">
		<property name="maxIdle" value="${redis.maxIdle}" />
		<property name="maxWaitMillis" value="${redis.maxWait}" />
        <!--<property name="maxActive" value="${redis.maxActive}" />-->
        <property name="testOnBorrow" value="${redis.testOnBorrow}" />
	</bean>

    <!--Connection factory creating-->
	<bean id="connectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory"
        p:hostName="${redis.host}" p:port="${redis.port}"
        p:password="${redis.password}" p:poolConfig-ref="poolConfig" p:database="${redis.database}" />

    <!--Helper class that simplifies Redis data access code-->
	<bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">
		<property name="connectionFactory" ref="connectionFactory" />
	</bean>
```
### 3. 实现RedisUtils<a>RedisUtils</a>的帮助类


### 参考：
#### 1. http://blog.csdn.net/java2000_wl/article/details/8543203/
#### 2. https://www.ibm.com/developerworks/cn/java/os-springredis/
#### 3. 官网：http://projects.spring.io/spring-data-redis/#quick-start
#### 4. [SpringAOP与Redis搭建缓存](http://www.cnblogs.com/mrlinfeng/p/5857775.html)