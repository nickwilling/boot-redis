package com.ecnu.wwl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component //把普通的类通过注解的方式放入spring容器管理，需要的时候autowired一下就能给我这个类
public class JedisUtil {
    @Autowired
    private JedisPool jedisPool;
//    获得Jedis对象
    public Jedis getJedis(){
        return jedisPool.getResource();
    }
//    关闭Jedis连接
    public void closeJedis(Jedis jedis){
        if (jedis!=null)    jedis.close();
    }

    public long calTimeHour(int time) {
        return time*60*60;
    }
}
