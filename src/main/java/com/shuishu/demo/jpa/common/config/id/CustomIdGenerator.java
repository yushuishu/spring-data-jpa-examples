package com.shuishu.demo.jpa.common.config.id;


import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;

/**
 * @author ：shuishu
 * @date   ：2022/3/22 14:09
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */

public class CustomIdGenerator extends JpaIdGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return generate(session, 1);
    }
}
