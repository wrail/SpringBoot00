package com.wrial.cahcedemo;

import com.wrial.cahcedemo.dao.Employee;
import com.wrial.cahcedemo.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CahcedemoApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSelect(){
        Employee empById = employeeMapper.getEmpById(1);
      log.info("select->{}",empById);
    }

}
