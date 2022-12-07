package com.shuishu.demo.jpa.service;


import com.shuishu.demo.jpa.common.config.domain.PageDTO;
import com.shuishu.demo.jpa.common.config.domain.PageVO;
import com.shuishu.demo.jpa.common.domain.dto.LibraryCollectionDTO;
import com.shuishu.demo.jpa.common.domain.dto.UserDTO;
import com.shuishu.demo.jpa.common.domain.po.User;
import com.shuishu.demo.jpa.common.domain.vo.LibraryCollectionVO;
import com.shuishu.demo.jpa.common.domain.vo.ProductVO;
import com.shuishu.demo.jpa.common.domain.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * @author ：shuishu
 * @date   ：2022/11/5 9:28
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */

public interface QuerydslService {
    /**
     * 空数据，返回对象
     */
    void findNullDataList();

    /**
     * 等于某个值的-结果集
     * @param userAge -用户年龄
     * @return -
     */
    List<UserVO> findUseEqList(Integer userAge);

    /**
     * 等于某个值的-单个对象
     * @param userId -用户id
     * @return -
     */
    User findByUserId(Long userId);

    /**
     * 条件封装的使用(BooleanBuilder)
     * @param userDTO -
     * @return -
     */
    List<UserVO> findUseBooleanBuilderList(UserDTO userDTO);

    /**
     * 模糊分页的用法-任意位置 like page
     * @param libraryCollectionDTO -
     * @param pageDTO -
     * @return -
     */
    PageVO<LibraryCollectionVO> findUseLikePage(LibraryCollectionDTO libraryCollectionDTO, PageDTO pageDTO);

    /**
     * 模糊检索的用法2-开始位置 StartsWith
     * @param userName -
     * @return -
     */
    List<UserVO> findUseStartsWith(String userName);

    /**
     * 模糊检索的用法3-区间 Between
     * @param libraryCollectionDTO -
     * @return -
     */
    List<LibraryCollectionVO> findUseBetween(LibraryCollectionDTO libraryCollectionDTO);

    /**
     * SQL中的语法IN
     * @param userAgeList -年龄集合
     * @return -
     */
    List<UserVO> findUseInList(List<Integer> userAgeList);

    /**
     * 聚合函数-groupBy
     * @return -
     */
    List<UserVO> findUseGroupByList();

    /**
     * 聚合函数-avg()
     * @return -
     */
    UserVO findUseAvg();

    /**
     * 聚合函数-sum()
     * @return -
     */
    UserVO findUseSum();

    /**
     * 聚合函数-concat()
     * @return -
     */
    List<UserVO> findUseConcatList();

    /**
     * 聚合函数-contains()
     * @param userName - 用户名
     * @return -
     */
    List<UserVO> findUseContainsList(String userName);

    /**
     * 聚合函数-DATE_FORMAT()
     * @return -
     */
    List<UserVO> findUseDateFormatList();

    /**
     * 字符模板的使用
     * @param libraryCollectionDTO -
     * @return -
     */
    List<LibraryCollectionVO> findStringTemplateUseList(LibraryCollectionDTO libraryCollectionDTO);

    /**
     * StringExpression 使用，分割字段值
     * @return -
     */
    Map<String, Long> findStringExpressionUseList();

    /**
     * 多表连接查询
     * @return -
     */
    List<UserVO> findMultiTableJoinList();

    /**
     * 一对多
     * @return -产品订单
     */
    List<ProductVO> findOneToManyList();

    /**
     * 一对多 （条件筛选 并 分页）
     * @param userName - 用户名
     * @param productCount - 产品库存
     * @param pageDTO  - 分页
     * @return - 产品订单
     */
    PageVO<ProductVO> findOneToManyConditionPage(String userName, Integer productCount, PageDTO pageDTO);



}
