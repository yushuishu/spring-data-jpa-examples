package com.shuishu.demo.jpa.common.dsl;


import com.shuishu.demo.jpa.common.config.jdbc.BaseDsl;
import com.shuishu.demo.jpa.common.domain.po.Department;
import com.shuishu.demo.jpa.common.domain.po.QDepartment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wuZhenFeng
 * @date 2022/11/5 9:15
 */
@Component
public class DepartmentDsl extends BaseDsl {
    private QDepartment qDepartment = QDepartment.department;


    public List<Department> findDepartmentList() {
        return jpaQueryFactory.selectFrom(qDepartment).fetch();
    }

}
