package com.ecnu.wwl.service;

import com.ecnu.wwl.po.User;

public interface UserService {
    /*
    * Redis String 类型
    * 需求：用户输入一个key
    * 先判断Redis中是否存在该数据
    *   如果存在，在Redis中进行查询并返回
    *   如果不存在，在MySQL数据库查询，将结果赋给Redis并返回。（因为Redis的存在就是为了减轻数据库的压力）
    * Redis支持高并发，数据库不支持高并发
    * 所以将数据库中常用的数据查出来放到redis中就可以实现查询的高并发
    * */

    public String getString(String key);


    /*
    * 测试String类型
    * 需求：用户输入一个redis数据，该key的有效期为28小时
    * */
    public void expireStr(String key,String value);


    /*
    * 测试Hash类型
    * */
    public User selectById(String id);
}
