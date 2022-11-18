package com.shuishu.demo.jpa.service.impl;


import com.shuishu.demo.jpa.common.config.exception.BusinessException;
import com.shuishu.demo.jpa.common.domain.Department;
import com.shuishu.demo.jpa.common.domain.NullData;
import com.shuishu.demo.jpa.common.dsl.DepartmentDsl;
import com.shuishu.demo.jpa.common.dsl.NullDataDsl;
import com.shuishu.demo.jpa.common.dsl.UserDsl;
import com.shuishu.demo.jpa.common.repository.DepartmentRepository;
import com.shuishu.demo.jpa.common.repository.NullDataRepository;
import com.shuishu.demo.jpa.service.QuerydslService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    private QuerydslServiceImpl querydslService;

    @Override
    public void findNullDataList() {
        List<NullData> all = nullDataRepository.findAll();
        List<NullData> all2 = nullDataDsl.findAll();
        NullData data1 = nullDataRepository.findFirstByDataId(1L);
        NullData data2 = nullDataDsl.findByDataId(1L);

        System.out.println(all == null);
        System.out.println(all2 == null);
        System.out.println(data1 == null);
        System.out.println(data2 == null);
    }

}
