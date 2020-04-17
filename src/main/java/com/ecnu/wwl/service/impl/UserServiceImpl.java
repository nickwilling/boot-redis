package com.ecnu.wwl.service.impl;

import com.ecnu.wwl.config.JedisUtil;
import com.ecnu.wwl.po.User;
import com.ecnu.wwl.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@Service
@Log //和 private Logger logger = LoggerFactory.getLogger(JedisConfig.class);代码作用一样，用来打印日志信息
public class UserServiceImpl implements UserService {
    /*#################TODO String类型的演示
     * Redis String 类型
     * 需求：用户输入一个key
     * 先判断Redis中是否存在该数据
     * 如果存在，在Redis中进行查询并返回
     * 如果不存在，在MySQL数据库查询，将结果赋给Redis并返回
     */
    @Autowired
    private JedisPool jedisPool; //Jedid连接池

    @Override
    public String getString(String key) {
        String val = "";
        //1、通过jedisPool获得jedis连接信息,连接开启了
        Jedis jedis = jedisPool.getResource();
        //2、判断 key 是否存在: redis命令---exists key
        if (jedis.exists(key)) {
            log.info("查询Redis中的数据");
            val = jedis.get(key);
        }else{
            //3、如果不存在，在MySQL数据库查询
            val = "南京路"; //代表MySQL查出来的值是val
            log.info("查询的是MySQL数据库"+val);
            //将MySQL数据库中查出来的值赋给redis
            jedis.set(key,val);
        }
//        4、关闭数据库连接
        jedis.close();
        return val;
    }
    @Autowired
    private JedisUtil jedisUtil;
    public void expireStr(String key,String value){
        int time = 28;
        Jedis jedis = jedisUtil.getJedis();
        jedis.set(key, value);//默认永久有效
//        比方说设置 28小时过期 那么expire的参数是以秒为单位的，这样就可以在工具类里定义一个计算秒的函数。
        jedis.expire(key, (int) jedisUtil.calTimeHour(time));
        jedis.close();
    }

    /*
    * ########################TODO Hash类型演示
    *存一个对象
    * 需求分析：根据用户的ID查询用户信息
    *   用户在前端输入一个ID编号
    *   根据用户的 ID查询用户对象的对象信息
    *   如果Redis存在，直接返回给用户结果并返回
    *   如果Redis中不存在，查询MySQL，并将查询到的结果赋值给Redis并返回
    * */
    @Override
    public User selectById(String id){
//        key有命名规范（实体类名：id）
        String className = "user";
        User u;
        String key = className+':'+id;
        Jedis jedis = jedisPool.getResource();
        if (jedis.exists(key)){
            log.info("查询redis");
            u = new User(jedis.hgetAll(key).get("id"),jedis.hgetAll(key).get("name"),Integer.parseInt(jedis.hgetAll(key).get("age")));
        } else{
            log.info("查询数据库");
            u = new User("2","mimi",18);
            Map<String,String> m = new HashMap();
            m.put("id",u.getId());
            m.put("name",u.getName());
            m.put("age",u.getAge().toString());
            jedis.hset(key,m);
        }
        jedisUtil.closeJedis(jedis);
        return u;
    }

}
