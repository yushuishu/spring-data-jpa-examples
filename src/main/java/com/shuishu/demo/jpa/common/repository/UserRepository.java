package com.shuishu.demo.jpa.common.repository;


import com.shuishu.demo.jpa.common.config.jdbc.BaseRepository;
import com.shuishu.demo.jpa.common.domain.po.User;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

/**
 * @author shuishu
 * @date 2022/10/29 17:22
 */

public interface UserRepository extends BaseRepository<User, Long> {
    /**
     * 行锁：并发查询 修改
     * @param userId -用户id
     * @return -
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select a from User a where a.userId = :userId")
    Optional<User> findByIdForUpdate(@Param("userId") Long userId);

    /**
     * 获取用户信息
     * @param userId -
     * @return -
     */
    User findFirstByUserId(Long userId);
}
