package com.wrial.cahcedemo.controller;

import com.wrial.cahcedemo.dao.Employee;
import com.wrial.cahcedemo.mapper.EmployeeMapper;
import com.wrial.cahcedemo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id) {
        Employee emp = employeeService.getEmp(id);
        return emp;
    }

    //测试自定义缓存策略
    @GetMapping("/emp01/{id}")
    public Employee getEmployee01(@PathVariable("id") Integer id) {
        Employee emp = employeeService.getEmp02(id);
        return emp;
    }

    //测试自定义KeyGenerator
    @GetMapping("/emp02/{id}")
    public Employee getEmployee02(@PathVariable("id") Integer id) {
        Employee emp = employeeService.getEmp03(id);
        return emp;
    }

    //测试Condition，如果id大于1才给缓存
    @GetMapping("/emp03/{id}")
    public Employee getEmployee03(@PathVariable("id") Integer id) {
        Employee emp = employeeService.getEmp04(id);
        return emp;
    }

    //测试Condition，如果id大于1才给缓存,unless如果满足就不缓存,因此一号二号都不缓存
    @GetMapping("/emp04/{id}")
    public Employee getEmployee04(@PathVariable("id") Integer id) {
        Employee emp = employeeService.getEmp05(id);
        return emp;
    }

    @GetMapping("/emp/update")
    public Employee updateEmp(Employee employee) {
        Employee employee1 = employeeService.updateEmp(employee);
        return employee1;
    }

    //根据特定id删除指定缓存
    @GetMapping("/emp/delete/{id}")
    public void deleteEmp(@PathVariable("id") Integer id) {
        employeeService.deleteEmp(id);
        log.info("success!");
    }

    //删除所有缓存数据
    @GetMapping("/emp/deleteAll")
    public void deleteAllEmp() {
        employeeService.deleteAllEmp();
        log.info("deleteAllEmp Success!");
    }

    //在方法执行之前删除缓存
    @GetMapping("/emp/deleteBefore/{id}")
    public void deleteEmpBefore(@PathVariable("id") Integer id) {
        employeeService.deleteEmpBefore(id);
        log.info("deleteBefore!");
    }

    @GetMapping("/emp/lastName/{lastName}")
    public void getEmpByLastName(@PathVariable("lastName") String lastName) {
        Employee empByLastName = employeeService.getEmpByLastName(lastName);
        log.info("empByLastName->{}", empByLastName);
    }


}
