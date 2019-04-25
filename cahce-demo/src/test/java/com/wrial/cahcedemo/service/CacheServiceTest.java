package com.wrial.cahcedemo.service;

import com.wrial.cahcedemo.dao.Employee;
import com.wrial.cahcedemo.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheServiceTest {

    @Autowired
    EmployeeMapper employeeMapper;

    //使用默认的缓存Key,生成策略是传入的参数
    @Cacheable(cacheNames = "#{emp}")//标识可缓存（运行结果）
    public Employee getEmp01(Integer id) {
        return employeeMapper.getEmpById(id);
    }

    //使用Spel自定义Key,比如自定义生成方法名和id的组合
    @Cacheable(cacheNames = "#{emp}", key = "#root.methodName+'['+'#{id}'+']'")
    public Employee getEmp02(Integer id) {
        return employeeMapper.getEmpById(id);
    }


}
