package com.shuishu.demo.jpa.common.config.jdbc;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shuishu.demo.jpa.common.config.id.IdGenerate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author ：shuishu
 * @date   ：2022/3/22 14:08
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：JPA配置
 */

@Configuration
public class JpaQueryConfiguration {
    /**
     * id 默认生成器
     *
     * @return idGenerate
     */
    @Bean
    public IdGenerate idGenerate() {
        return new IdGenerate(0, 0);
    }

    @Resource
    @PersistenceContext
    public EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
