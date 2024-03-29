package cn.manager.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 启动类
 *
 * @author ljc
 * @version 1.0.0
 * @description ok
 */
//@EnableScheduling
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("cn.manager.system.mapper")
public class CarApplication {

    public static void main(String[] args) {

        SpringApplication.run(CarApplication.class, args);
    }

}
