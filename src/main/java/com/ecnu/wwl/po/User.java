package com.ecnu.wwl.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data //@Data 注解(属于lombok)的主要作用是提高代码的简洁，使用这个注解可以省去代码中大量的get()、 set()、 toString()等方法；
@AllArgsConstructor//提供全参数的构造函数
@NoArgsConstructor//提供无参数的构造函数
public class User implements Serializable {
    private String id;
    private String name;
    private Integer age;
}
