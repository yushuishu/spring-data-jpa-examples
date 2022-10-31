package com.shuishu.demo.jpa.common.config.swagger;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shuishu
 * @date 2022/3/22 14:41
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiRequestExclude {
    Class<?>[] groups() default {};
}
