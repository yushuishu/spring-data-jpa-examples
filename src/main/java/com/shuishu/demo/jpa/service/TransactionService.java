package com.shuishu.demo.jpa.service;


/**
 * @author ：shuishu
 * @date   ：2022/10/29 8:38
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */

public interface TransactionService {
    /**
     * 并发更新测试 不加锁，querydsl中add方法
     * 结果：数据正常
     * @param userId -
     */
    void updateForConcurrence(Long userId);

    /**
     * 并发更新测试 加行锁，querydsl中add方法
     * 结果：数据正常
     * @param userId -
     */
    void updateForConcurrenceLock(Long userId);

    /**
     * 持久态:普通查询，set是否更新
     * 结果：数据被更新（hibernate的对象持久态）
     * @param userId -
     */
    void findForSet(Long userId);

    /**
     * 持久态:并发查询，set操作，代码逻辑上查询出来进行累加
     * 结果：数据错误
     * @param userId -
     */
    void findForConcurrenceSet(Long userId);

    /**
     * 持久态:并发查询，set是否更新(加锁)，代码逻辑上查询出来进行累加
     * 结果：数据正常
     * @param userId -
     */
    void findForConcurrenceLockSet(Long userId);

    /**
     * 游离态:普通查询，set是否更新
     * 结果：数据没有更新 （数据没变化，就没必要测试 并发操作了）
     * @param userId -
     */
    void find2ForSet(Long userId);

    /**
     * 临时态:普通查询，set是否更新
     * 结果：数据没有更新 （数据没变化，就没必要测试 并发操作了）
     * @param userId -
     */
    void find3ForSet(Long userId);

}
