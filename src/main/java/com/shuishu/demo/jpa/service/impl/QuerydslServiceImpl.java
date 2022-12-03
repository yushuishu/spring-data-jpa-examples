package com.shuishu.demo.jpa.service.impl;


import cn.hutool.core.date.DateUtil;
import com.shuishu.demo.jpa.common.config.domain.PageDTO;
import com.shuishu.demo.jpa.common.config.domain.PageVO;
import com.shuishu.demo.jpa.common.domain.dto.LibraryCollectionDTO;
import com.shuishu.demo.jpa.common.domain.dto.UserDTO;
import com.shuishu.demo.jpa.common.domain.po.NullData;
import com.shuishu.demo.jpa.common.domain.po.User;
import com.shuishu.demo.jpa.common.domain.vo.LibraryCollectionVO;
import com.shuishu.demo.jpa.common.domain.vo.ProductVO;
import com.shuishu.demo.jpa.common.domain.vo.UserVO;
import com.shuishu.demo.jpa.common.dsl.*;
import com.shuishu.demo.jpa.common.repository.*;
import com.shuishu.demo.jpa.service.QuerydslService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author wuZhenFeng
 * @date 2022/11/5 9:28
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class QuerydslServiceImpl implements QuerydslService {
    @Resource
    private NullDataRepository nullDataRepository;
    @Resource
    private NullDataDsl nullDataDsl;
    @Resource
    private DepartmentRepository departmentRepository;
    @Resource
    private DepartmentDsl departmentDsl;
    @Resource
    private UserDsl userDsl;
    @Resource
    private ProductRepository productRepository;
    @Resource
    private ProductDsl productDsl;
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private OrderDsl orderDsl;
    @Resource
    private LibraryCollectionRepository libraryCollectionRepository;
    @Resource
    private LibraryCollectionDsl libraryCollectionDsl;
    @Resource
    private QuerydslServiceImpl querydslService;


    @Override
    public void findNullDataList() {
        List<NullData> all = nullDataRepository.findAll();
        List<NullData> all2 = nullDataDsl.findAll();
        NullData data1 = nullDataRepository.findFirstByDataId(1L);
        NullData data2 = nullDataDsl.findByDataId(1L);

        //false
        //false
        //true
        //true
        System.out.println(all == null);
        System.out.println(all2 == null);
        System.out.println(data1 == null);
        System.out.println(data2 == null);
    }

    @Override
    public List<UserVO> findUseEqList(Integer userAge) {
        Objects.requireNonNull(userAge, "用户年龄不能为空！");
        return userDsl.findUseEqList(userAge);
    }

    @Override
    public User findByUserId(Long userId) {
        Objects.requireNonNull(userId, "用户id不能为空！");
        return userDsl.findByUserId(userId);
    }

    @Override
    public List<UserVO> findUseBooleanBuilderList(UserDTO userDTO) {
        return userDsl.findUseBooleanBuilderList(userDTO);
    }

    @Override
    public PageVO<LibraryCollectionVO> findUseLikePage(LibraryCollectionDTO libraryCollectionDTO, PageDTO pageDTO) {
        libraryCollectionDTO.setStartDate(disposeDate(libraryCollectionDTO.getStartDate(), null));
        libraryCollectionDTO.setEndDate(disposeDate(null, libraryCollectionDTO.getEndDate()));
        return libraryCollectionDsl.findUseLikePage(libraryCollectionDTO, pageDTO);
    }

    @Override
    public List<UserVO> findUseStartsWith(String userName) {
        return userDsl.findUseStartsWith(userName);
    }

    @Override
    public List<LibraryCollectionVO> findUseBetween(LibraryCollectionDTO libraryCollectionDTO) {
        libraryCollectionDTO.setStartDate(disposeDate(libraryCollectionDTO.getStartDate(), null));
        libraryCollectionDTO.setEndDate(disposeDate(null, libraryCollectionDTO.getEndDate()));
        return libraryCollectionDsl.findUseBetween(libraryCollectionDTO.getStartDate(), libraryCollectionDTO.getEndDate());
    }

    @Override
    public List<UserVO> findUseInList(List<Integer> userAgeList) {
        return userDsl.findUseInList(userAgeList);
    }

    @Override
    public List<UserVO> findUseGroupByList() {
        return userDsl.findUseGroupByList();
    }

    @Override
    public UserVO findUseAvg() {
        UserVO userVO = new UserVO();
        userVO.setUserAgeAvg(userDsl.findUseAvg());
        return userVO;
    }

    @Override
    public UserVO findUseSum() {
        UserVO userVO = new UserVO();
        userVO.setIntegrateSum(userDsl.findUseSum());
        return userVO;
    }

    @Override
    public List<UserVO> findUseConcatList() {
        return userDsl.findUseConcatList();
    }

    @Override
    public List<UserVO> findUseDateFormatList() {
        return userDsl.findUseDateFormatList();
    }

    @Override
    public List<LibraryCollectionVO> findStringTemplateUseList(LibraryCollectionDTO libraryCollectionDTO) {
        libraryCollectionDTO.setStartDate(disposeDate(libraryCollectionDTO.getStartDate(), null));
        libraryCollectionDTO.setEndDate(disposeDate(null, libraryCollectionDTO.getEndDate()));
        return libraryCollectionDsl.findStringTemplateUseList(libraryCollectionDTO);
    }

    @Override
    public Map<String, Long> findStringExpressionUseList() {
        return libraryCollectionDsl.findStringExpressionUseList();
    }

    @Override
    public List<UserVO> findMultiTableJoinList() {
        return userDsl.findMultiTableJoinList();
    }

    @Override
    public List<ProductVO> findOneToManyList() {
        return productDsl.findOneToManyList();
    }

    @Override
    public PageVO<ProductVO> findOneToManyConditionPage(String userName, Integer productCount, PageDTO pageDTO) {
        return productDsl.findOneToManyConditionPage(userName, productCount, pageDTO);
    }

    private Date disposeDate(Date startDate, Date endDate){
        if (startDate != null){
            return DateUtil.beginOfDay(startDate);
        }
        if (endDate != null){
            return DateUtil.endOfDay(endDate);
        }
        return null;
    }

}
