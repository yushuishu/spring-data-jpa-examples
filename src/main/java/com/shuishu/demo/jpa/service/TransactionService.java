package com.shuishu.demo.jpa.service;


import com.shuishu.demo.jpa.common.domain.User;

/**
 * @author shuishu
 * @date 2022/10/29 8:38
 */

public interface TransactionService {
    /**
     * 并发更新测试 不加锁
     * @param userId -
     */
    void updateForConcurrence(Long userId);

    /**
     * 并发更新测试 加行锁
     * @param userId -
     */
    void updateForConcurrenceLock(Long userId);

    /**
     * 普通查询，set是否更新
     * @param userId -
     */
    void findForSet(Long userId);

    /**
     * 并发查询，set操作
     * @param userId -
     */
    void findForConcurrenceSet(Long userId);

    /**
     * 并发查询，set是否更新(加锁)
     * @param userId -
     */
    void findForConcurrenceLockSet(Long userId);

    /**
     * 获取用户信息
     * @param userId -
     * @return -
     */
    User getUser(Long userId);
}
