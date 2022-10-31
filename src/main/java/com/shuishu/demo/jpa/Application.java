package com.shuishu.demo.jpa;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author shuishu
 * 2022/10/29 9:40
 *
 * EnableJpaRepositories: 扫描继承 BaseRepository 的类
 * EntityScan: 扫描实体类
 * EnableJpaAuditing: 1、jpa开启实体类监听 2、开启实体类字段注解
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.shuishu.demo.jpa.**", "cn.hutool.extra.spring"})
@EnableJpaRepositories("com.shuishu.demo.jpa.common.**")
@EntityScan("com.shuishu.demo.jpa.common.**")
@EnableJpaAuditing
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
