package com.shuishu.demo.jpa.common.config.id;


import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author ：shuishu
 * @date   ：2022/3/22 14:11
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */

public class JpaIdGenerator extends IdentityGenerator {
    public Serializable generate(Object object, long workerId) {
        // 如果传进来的 主键不为空，直接返回
        try {
            Class<?> cls = object.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    Object value = field.get(object);
                    if (value != null) {
                        return (Serializable) value;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 主键为空，添加id
        return IdGenerateUtil.getInstance().getId(workerId);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return generate(object, 0);
    }
}
