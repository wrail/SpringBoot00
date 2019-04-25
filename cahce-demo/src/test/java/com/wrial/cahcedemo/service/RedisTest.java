package com.wrial.cahcedemo.service;

import com.wrial.cahcedemo.dao.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisTest {

    //为什么会分为两个，因为在平时用字符串比较多，后者是专门来处理字符串的K-V，前者可以处理任何对象的K-V
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    EmployeeService employeeService;
    @Autowired
   RedisTemplate empRedisTemplate;

    /**
     * Redis中常用操作
     * 五大数据类型：String（字符串），List（列表），Set（集合），Hash（散列），ZSet（有序集合）
     * stringRedisTemplate.opsForHash();
     * stringRedisTemplate.opsForList();
     * stringRedisTemplate.opsForSet();
     */
    @Test
    public void test01() {
//        redisTemplate.opsForValue().append("wrial", "666");
//        redisTemplate.opsForValue().set("wrial",666);
        stringRedisTemplate.opsForValue().set("wrial", "666");
        Object wrial = stringRedisTemplate.opsForValue().get("wrial");
        log.info("wrial->{}", wrial);
    }

    //使用Redis来缓存对象,传入的对象必须是可序列化的
    @Test
    public void test02() {
        Employee emp = employeeService.getEmp(1);
        redisTemplate.opsForValue().set("emp1", emp);
    }
    //使用自定义的Json序列化来保存对象
    /*
    * 1.编写配置类，并且在配置类中设置默认的序列化方法
    * 2.注入我们自定义的序列化模板
    * 3.测试
    * */
    @Test
    public void test03(){
        Employee emp = employeeService.getEmp(1);
        empRedisTemplate.opsForValue().set("myemp",emp);

    }


}
