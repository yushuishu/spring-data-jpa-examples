package com.shuishu.demo.jpa.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shuishu.demo.jpa.common.config.exception.ApiResponse;
import com.shuishu.demo.jpa.service.QuerydslService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuZhenFeng
 * @date 2022/11/5 9:27
 */
@Api(tags = "Querydsl使用")
@RestController
@RequestMapping("/querydsl")
public class QuerydslController {
    @Resource
    private QuerydslService querydslService;

    @ApiOperationSupport(order = 1)
    @ApiOperation("空表数据查询")
    @GetMapping("department/list")
    public ApiResponse<String> findNullDataList(){
        querydslService.findNullDataList();
        return ApiResponse.success();
    }



}
