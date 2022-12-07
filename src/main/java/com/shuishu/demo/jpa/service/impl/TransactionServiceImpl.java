package com.shuishu.demo.jpa.service.impl;


import com.shuishu.demo.jpa.common.config.exception.BusinessException;
import com.shuishu.demo.jpa.common.domain.po.User;
import com.shuishu.demo.jpa.common.dsl.UserDsl;
import com.shuishu.demo.jpa.common.repository.UserRepository;
import com.shuishu.demo.jpa.service.TransactionService;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 * @author ：shuishu
 * @date   ：2022/10/29 8:38
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class TransactionServiceImpl implements TransactionService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserDsl userDsl;
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void updateForConcurrence(Long userId) {
        System.out.println("当前线程：" + Thread.currentThread().getName());
        User user = userRepository.findFirstByUserId(userId);
        // sql add 累加测试
        userDsl.updateIntegrate(user.getUserId(),1);
    }

    @Override
    public void updateForConcurrenceLock(Long userId) {
        System.out.println("当前线程：" + Thread.currentThread().getName());
        Optional<User> userOptional = userRepository.findByIdForUpdate(userId);
        if (!userOptional.isPresent()){
            throw new BusinessException("用户不存在！");
        }
        User user = userOptional.get();
        // sql add 累加测试
        userDsl.updateIntegrate(user.getUserId(),1);
    }

    @Override
    public void findForSet(Long userId) {
        User user = userDsl.findByUserId(userId);
        System.out.println(user.getIntegrate());
        user.setIntegrate(666);

        // 持久态对象set操作之后，更新SQL时间：1、只有再次查询表数据时，会先执行跟新语句。2、当前事务结束
        System.out.println("-------------------- 1");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user2 = userDsl.findByUserId(userId);
        System.out.println(user2);
        System.out.println("-------------------- 2");
    }

    @Override
    public void findForConcurrenceSet(Long userId) {
        System.out.println("当前线程：" + Thread.currentThread().getName());
        User user = userRepository.findFirstByUserId(userId);
        System.out.println(user.getIntegrate());
        user.setIntegrate(user.getIntegrate() + 1);
    }

    @Override
    public void findForConcurrenceLockSet(Long userId) {
        System.out.println("当前线程：" + Thread.currentThread().getName());
        Optional<User> userOptional = userRepository.findByIdForUpdate(userId);
        if (!userOptional.isPresent()){
            throw new BusinessException("用户不存在！");
        }
        User user = userOptional.get();
        user.setIntegrate(user.getIntegrate() + 1);
    }

    @Override
    public void find2ForSet(Long userId) {
        User user = userDsl.findByUserId(userId);
        System.out.println(user.getIntegrate());

        // 持久态对象 转换为 游离态对象

        entityManager.unwrap(Session.class).evict(user);

        user.setIntegrate(666);
    }

    @Override
    public void find3ForSet(Long userId) {
        User dbUser = userDsl.findByUserId(userId);
        System.out.println(dbUser.getIntegrate());

        // new 操作，对象是瞬时态
        User user = new User();
        BeanUtils.copyProperties(dbUser,user);
        user.setIntegrate(666);
    }

}
