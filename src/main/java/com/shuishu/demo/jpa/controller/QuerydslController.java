package com.shuishu.demo.jpa.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shuishu.demo.jpa.common.config.domain.PageDTO;
import com.shuishu.demo.jpa.common.config.domain.PageVO;
import com.shuishu.demo.jpa.common.config.exception.ApiResponse;
import com.shuishu.demo.jpa.common.config.swagger.ApiGroup;
import com.shuishu.demo.jpa.common.domain.dto.LibraryCollectionDTO;
import com.shuishu.demo.jpa.common.domain.dto.UserDTO;
import com.shuishu.demo.jpa.common.domain.po.User;
import com.shuishu.demo.jpa.common.domain.vo.LibraryCollectionVO;
import com.shuishu.demo.jpa.common.domain.vo.ProductVO;
import com.shuishu.demo.jpa.common.domain.vo.UserVO;
import com.shuishu.demo.jpa.service.QuerydslService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author ：shuishu
 * @date   ：2022/11/5 9:27
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
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

    @ApiOperationSupport(order = 3)
    @ApiOperation("等于某个值的-结果集")
    @GetMapping("eq/list")
    public ApiResponse<List<UserVO>> findUseEqList(Integer userAge){
        return ApiResponse.of(querydslService.findUseEqList(userAge));
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("等于某个值的-单个对象")
    @GetMapping("eq/find")
    public ApiResponse<User> findUseEq(Long userId){
        return ApiResponse.success(querydslService.findByUserId(userId));
    }

    @ApiOperationSupport(order = 9)
    @ApiOperation("条件封装的使用(BooleanBuilder)")
    @ApiGroup(UserDTO.FindUseBooleanBuilderList.class)
    @GetMapping("booleanBuilder/find")
    public ApiResponse<List<UserVO>> findUseBooleanBuilderList(UserDTO userDTO){
        return ApiResponse.of(querydslService.findUseBooleanBuilderList(userDTO));
    }

    @ApiOperationSupport(order = 12)
    @ApiOperation("模糊分页的用法-任意位置")
    @ApiGroup(LibraryCollectionDTO.FindUseLikePage.class)
    @GetMapping("like/page")
    public ApiResponse<PageVO<LibraryCollectionVO>> findUseLikePage(LibraryCollectionDTO libraryCollectionDTO, PageDTO pageDTO){
        return ApiResponse.of(querydslService.findUseLikePage(libraryCollectionDTO, pageDTO));
    }

    @ApiOperationSupport(order = 15)
    @ApiOperation("模糊检索的用法2-开始位置")
    @GetMapping("startsWith/list")
    public ApiResponse<List<UserVO>> findUseStartsWith(String userName){
        return ApiResponse.of(querydslService.findUseStartsWith(userName));
    }

    @ApiOperationSupport(order = 18)
    @ApiOperation("模糊检索的用法3-区间")
    @ApiGroup(LibraryCollectionDTO.FindUseBetween.class)
    @GetMapping("between/list")
    public ApiResponse<List<LibraryCollectionVO>> findUseBetween(LibraryCollectionDTO libraryCollectionDTO){
        return ApiResponse.of(querydslService.findUseBetween(libraryCollectionDTO));
    }

    @ApiOperationSupport(order = 22)
    @ApiOperation("SQL中的语法IN")
    @ApiGroup(UserDTO.FindUseInList.class)
    @PostMapping("in/list")
    public ApiResponse<List<UserVO>> findUseInList(@RequestBody UserDTO userDTO){
        return ApiResponse.of(querydslService.findUseInList(userDTO.getUserAgeList()));
    }

    @ApiOperationSupport(order = 25)
    @ApiOperation("排序Order(一个条件)")
    @PostMapping("OrderSpecifier/list")
    public ApiResponse<List<UserVO>> findUseOrderSpecifierList(){
        return ApiResponse.of(querydslService.findUseOrderSpecifierList());
    }

    @ApiOperationSupport(order = 28)
    @ApiOperation("排序Order(多个条件)")
    @PostMapping("more/OrderSpecifier/list")
    public ApiResponse<List<UserVO>> findUseMoreOrderSpecifierList(){
        return ApiResponse.of(querydslService.findUseMoreOrderSpecifierList());
    }

    @ApiOperationSupport(order = 28)
    @ApiOperation("排序Order(多个条件逻辑判断)")
    @PostMapping("more/logic/OrderSpecifier/list")
    public ApiResponse<List<UserVO>> findUseMoreLogicOrderSpecifierList(@RequestParam(name = "order", defaultValue = "DESC") String order){
        return ApiResponse.of(querydslService.findUseMoreLogicOrderSpecifierList(order));
    }

    @ApiOperationSupport(order = 31)
    @ApiOperation("聚合函数-groupBy")
    @GetMapping("groupBy/list")
    public ApiResponse<List<UserVO>> findUseGroupByList(){
        return ApiResponse.of(querydslService.findUseGroupByList());
    }

    @ApiOperationSupport(order = 35)
    @ApiOperation("聚合函数-avg()")
    @GetMapping("user/avg/find")
    public ApiResponse<UserVO> findUseAvg(){
        return ApiResponse.of(querydslService.findUseAvg());
    }

    @ApiOperationSupport(order = 38)
    @ApiOperation("聚合函数-sum()")
    @GetMapping("user/sum/find")
    public ApiResponse<UserVO> findUseSum(){
        return ApiResponse.of(querydslService.findUseSum());
    }

    @ApiOperationSupport(order = 41)
    @ApiOperation("聚合函数-concat()")
    @GetMapping("user/concat/find")
    public ApiResponse<List<UserVO>> findUseConcatList(){
        return ApiResponse.of(querydslService.findUseConcatList());
    }

    @ApiOperationSupport(order = 45)
    @ApiOperation("聚合函数-contains()")
    @GetMapping("user/contains/find")
    public ApiResponse<List<UserVO>> findUseContainsList(String userName){
        return ApiResponse.of(querydslService.findUseContainsList(userName));
    }

    @ApiOperationSupport(order = 48)
    @ApiOperation("聚合函数-DATE_FORMAT()")
    @GetMapping("user/date_format/find")
    public ApiResponse<List<UserVO>> findUseDateFormatList(){
        return ApiResponse.of(querydslService.findUseDateFormatList());
    }

    @ApiOperationSupport(order = 51)
    @ApiOperation("stringTemplate字符模板")
    @ApiGroup(LibraryCollectionDTO.FindStringTemplateUseList.class)
    @GetMapping("stringTemplate/use/list")
    public ApiResponse<List<LibraryCollectionVO>> findStringTemplateUseList(LibraryCollectionDTO libraryCollectionDTO){
        return ApiResponse.of(querydslService.findStringTemplateUseList(libraryCollectionDTO));
    }

    @ApiOperationSupport(order = 55)
    @ApiOperation("CASE...WHEN...THEN...")
    @GetMapping("stringExpression/list")
    public ApiResponse<Map<String, Long>> findStringExpressionUseList(){
        return ApiResponse.of(querydslService.findStringExpressionUseList());
    }

    @ApiOperationSupport(order = 59)
    @ApiOperation("多表连接查询")
    @GetMapping("multi/table/join/list")
    public ApiResponse<List<UserVO>> findMultiTableJoinList(){
        return ApiResponse.of(querydslService.findMultiTableJoinList());
    }

    @ApiOperationSupport(order = 63)
    @ApiOperation("一对多")
    @GetMapping("one_to_many/list")
    public ApiResponse<List<ProductVO>> findOneToManyList(){
        return ApiResponse.of(querydslService.findOneToManyList());
    }

    @ApiOperationSupport(order = 67)
    @ApiOperation("一对多(条件筛选并分页)")
    @GetMapping("one_to_many/condition/page")
    public ApiResponse<PageVO<ProductVO>> findOneToManyConditionPage(String userName, Integer productCount, PageDTO pageDTO){
        return ApiResponse.of(querydslService.findOneToManyConditionPage(userName, productCount, pageDTO));
    }


}
