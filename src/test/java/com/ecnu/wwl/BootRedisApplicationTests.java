package com.ecnu.wwl;

import com.ecnu.wwl.po.User;
import com.ecnu.wwl.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.JedisPool;

@SpringBootTest
class BootRedisApplicationTests {
    @Autowired
    private UserService userService;
    // 做Jedis测试
    @Autowired
    private JedisPool jedisPool;
    @Test
    void contextLoads() {
        System.out.println(jedisPool);
    }

    /*
    *模拟Jedis操作Redis String类型的数据
    * */
    @Test
    void t1(){
        String result = userService.getString("namekkk");
        System.out.println(userService);
        userService.expireStr("nnn","kkk");
        System.out.println(result);
    }

    @Test
    void t2(){
        userService.expireStr("testKey","testValue");
    }

    @Test
    void t3(){
        User u = userService.selectById("12");
        System.out.println(u.getId()+'\t'+u.getName()+'\t'+u.getAge());
    }
}
