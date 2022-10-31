package com.shuishu.demo.jpa.common.dsl;


import com.shuishu.demo.jpa.common.config.jdbc.BaseDsl;
import com.shuishu.demo.jpa.common.domain.QUser;
import com.shuishu.demo.jpa.common.domain.User;
import org.springframework.stereotype.Component;

/**
 * @author shuishu
 * @date 2022/10/29 17:22
 */
@Component
public class UserDsl extends BaseDsl {
    private QUser qUser = QUser.user;

    public void updateIntegrate(Long userId, int integrate){
        if (userId != null){
            jpaQueryFactory.update(qUser)
                    .set(qUser.integrate, qUser.integrate.add(integrate))
                    .where(qUser.userId.eq(userId))
                    .execute();
        }
    }

    public User findByUserId(Long userId) {
        return jpaQueryFactory.selectFrom(qUser).where(qUser.userId.eq(userId)).fetchFirst();
    }
}
