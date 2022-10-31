package com.shuishu.demo.jpa.service.impl;


import com.shuishu.demo.jpa.common.config.exception.BusinessException;
import com.shuishu.demo.jpa.common.domain.User;
import com.shuishu.demo.jpa.common.dsl.UserDsl;
import com.shuishu.demo.jpa.common.repository.UserRepository;
import com.shuishu.demo.jpa.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author shuishu
 * @date 2022/10/29 8:38
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class TransactionServiceImpl implements TransactionService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserDsl userDsl;


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
        //User user = userRepository.findFirstByUserId(userId);
        User user = userDsl.findByUserId(userId);

        System.out.println(user.getIntegrate());

        user.setIntegrate(666);
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
    public User getUser(Long userId) {
        return userRepository.findFirstByUserId(userId);
    }
}
