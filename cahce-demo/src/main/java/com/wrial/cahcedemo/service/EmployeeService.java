package com.wrial.cahcedemo.service;

import com.wrial.cahcedemo.dao.Employee;
import com.wrial.cahcedemo.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
//@CacheConfig//顾名思义，写在类上就是可以批量的声明某一类缓存，
// 或者是按照某一种生成更改规则，不用在每一个方法上写，减少了代码的重复性
@Slf4j
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;


    /**
     * Cacheable：可缓存
     * 缓存说明* 默认的采用SimpleCache，底层使用ConconcurrentMap。
     * <p>
     * cacheNames/value：二选一，指定缓存名，可以是一个数组类型
     * CacheManager：很多个缓存名所标识的缓存都让CacheManager来管理，也可以指定缓存管理器（如Redis等）
     * CacheResolver：和缓存管理器二选一
     * key：缓存使用的key，默认是传入的参数，也可以自定义keyGenerator，也可以使用Spel表达式
     * keyGenerator()：key的生成器，默认以传入的参数为key，也可以自定义，和key二选一
     * Condition：可以指定条件，也可以使用Spel（如#{id>0}）
     * Unless:否定缓存，当Unless为true，那就不会缓存，
     * 也可以获取到结果再判断（如#{result==null}），作用和Condition相反，但是可以一起使用进行双重判断
     * sysc：是否异步，用了以不就不能用unless了
     */

    //使用默认的缓存Key生成策略，也就是传入的参数
    @Cacheable(value = "#{emp}", key = "#id")//标识可缓存（运行结果）
    public Employee getEmp(Integer id) {
        return employeeMapper.getEmpById(id);
    }

    //使用Spel自定义Key,比如自定义生成方法名和id的组合
    @Cacheable(cacheNames = "#{emp}", key = "#root.methodName+'['+'#{id}'+']'")
    public Employee getEmp02(Integer id) {
        return employeeMapper.getEmpById(id);
    }

    //使用KeyGenerator自定义Key,比如自定义生成方法名和id的组合myKeyGenerator是自定义到bean里的
    @Cacheable(cacheNames = "#{emp}", keyGenerator = "myKeyGenerator")
    public Employee getEmp03(Integer id) {
        return employeeMapper.getEmpById(id);
    }

    //Condition，如果第一个参数大于1才给缓存
    @Cacheable(cacheNames = "#{emp}", condition = "#a0>1")
    public Employee getEmp04(Integer id) {
        return employeeMapper.getEmpById(id);
    }

    //如果第一个参数大于1才给缓存
    @Cacheable(cacheNames = "#{emp}", condition = "#a0>1", unless = "#a0==2")
    public Employee getEmp05(Integer id) {
        return employeeMapper.getEmpById(id);
    }


    /* @CachePut:既调用方法，又更新
     * 修改了数据库某个值同步更新
     *能导致缓存同步失败的几个原因？
     * 1.value不相同
     * 2.key不相同，在查询的时候是以id为key，如果update以employee对象key就会导致同步失败
     * 也可以使用#result.id
     * 和Cacheable的区别：Cacheable主要是在缓存前起作用进行一些列判断要不要缓存
     * 而cacheput是缓存后起作用，因此Cacheable不能使用result。
     * 测试步骤：
     * 1.先查询
     * 2.update
     * 3.查询
     */
    @CachePut(value = "#{emp}", key = "#employee.id")
    public Employee updateEmp(Employee employee) {
        log.info("员工更新信息-》{}", employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    /**
     * @CacheEvict 删除缓存
     * key 指定要删除的元素
     * allEntries = false 默认为false，如果为true删除当前value/name下的所有缓存
     * beforeInvocation = false 默认在方法执行之后删除数据，
     * 如果为true，那就在执行方法之前（可以在方法中写一个异常，查看是否清除异常）
     */

    @CacheEvict(value = "#{emp}", key = "#id")
    public void deleteEmp(Integer id) {
        System.out.println("deleteEmp" + id);
//        employeeMapper.deleteEmp(id);
    }

    @CacheEvict(value = "#{emp}", allEntries = true)
    public void deleteAllEmp() {
        System.out.println("deleteAllEmp");
    }

    @CacheEvict(value = "#{emp}", beforeInvocation = true, key = "#id")
    public void deleteEmpBefore(Integer id) {
        int i = 1 / 0;
        System.out.println("deleteEmpBefore");
    }

    /**
     * @Caching Cacheable，CachePut和CahceEvict的集合
     * public @interface Caching {
     * 	Cacheable[] cacheable() default {};
     * 	CachePut[] put() default {};
     * 	CacheEvict[] evict() default {};
     * }
     * 可以满足复杂的缓存条件
     * */

    @Caching(cacheable = {
            @Cacheable(value = "emp",key = "#lastName")
    },
    put = {
            @CachePut(value = "emp",key = "#result.lastName"),
            @CachePut(value = "#{emp}",key = "#result.id")//设置为#{emp}是为了测试上边的查询方法
    })
    public Employee getEmpByLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }

}
