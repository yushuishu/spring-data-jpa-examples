package com.shuishu.demo.jpa.common.config.swagger;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shuishu
 * @date 2022/3/22 14:42
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiRequestFieldRequired {
    Class<?>[] groups() default {};
}
