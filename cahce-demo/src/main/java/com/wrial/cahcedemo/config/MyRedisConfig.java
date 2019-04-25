package com.wrial.cahcedemo.config;

import com.wrial.cahcedemo.dao.Employee;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class MyRedisConfig {//可以参照RedisAutoConfiguration

    //先给Employee配置特定的序列化方式（Json）
    @Bean
    public RedisTemplate<Object, Employee> empRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Employee> template = new RedisTemplate<>();
        Jackson2JsonRedisSerializer<Employee> serializer = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
        template.setDefaultSerializer(serializer);//可以进入setDefaultSerializer中找想要的序列化方式
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
    //经过上边配置后我们在写入redis时可以采用自定义的Json格式写入，但是其他数据都还是以默认的JDKSerializable，
    // 因此需要对RedisCacheManager进行改变（比如在缓存其他数据的时候，底层就会从currentHashMap换为Redis）
    //因此数据库在出过使用Json序列化的以外，其他都是默认的JDK序列化
    //SpringBoot2怎么对它进行改变？
//
//    @Bean
//    public RedisCacheManager empRedisCacheManager(RedisTemplate<Object, Employee> empRedisTemplate) {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//    }
}
