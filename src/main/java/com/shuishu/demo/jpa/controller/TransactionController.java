package com.shuishu.demo.jpa.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shuishu.demo.jpa.common.config.exception.ApiResponse;
import com.shuishu.demo.jpa.common.domain.User;
import com.shuishu.demo.jpa.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author shuishu
 * @date 2022/10/29 8:38
 */
@Api(tags = "事务、并发")
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Resource
    private TransactionService transactionService;

    @ApiOperationSupport(order = 1)
    @ApiOperation("并发更新(不加锁)")
    @GetMapping("concurrence/update/{userId}")
    public ApiResponse<String> updateForConcurrence(@PathVariable("userId") Long userId){
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                transactionService.updateForConcurrence(userId);
            }, String.valueOf(i)).start();
        }
        return ApiResponse.success();
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation("并发更新(加锁)")
    @GetMapping("concurrence/lock/update/{userId}")
    public ApiResponse<String> updateForConcurrenceLock(@PathVariable("userId") Long userId){
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                transactionService.updateForConcurrenceLock(userId);
            }, String.valueOf(i)).start();
        }
        return ApiResponse.success();
    }

    @ApiOperationSupport(order = 3)
    @ApiOperation("普通查询，set是否更新")
    @GetMapping("find/{userId}")
    public ApiResponse<String> findForSet(@PathVariable("userId") Long userId){
        transactionService.findForSet(userId);
        return ApiResponse.success();
    }

    @ApiOperationSupport(order = 4)
    @ApiOperation("并发查询，set是否更新(不加锁)")
    @GetMapping("concurrence/find/{userId}")
    public ApiResponse<String> findForConcurrenceSet(@PathVariable("userId") Long userId){
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                transactionService.findForConcurrenceSet(userId);
            }, String.valueOf(i)).start();
        }
        return ApiResponse.success();
    }

    @ApiOperationSupport(order = 5)
    @ApiOperation("并发查询，set是否更新(加锁)")
    @GetMapping("concurrence/lock/find/{userId}")
    public ApiResponse<String> findForConcurrenceLockSet(@PathVariable("userId") Long userId){
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                transactionService.findForConcurrenceLockSet(userId);
            }, String.valueOf(i)).start();
        }
        return ApiResponse.success();
    }

    @ApiOperationSupport(order = 10)
    @ApiOperation("获取用户信息")
    @GetMapping("get/{userId}")
    public ApiResponse<User> getUser(@PathVariable("userId") Long userId){
        return ApiResponse.of(transactionService.getUser(userId));
    }
    
    
}
