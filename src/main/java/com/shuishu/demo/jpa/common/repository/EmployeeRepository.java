package com.shuishu.demo.jpa.common.repository;


import com.shuishu.demo.jpa.common.config.jdbc.BaseRepository;
import com.shuishu.demo.jpa.common.domain.Employee;

/**
 * @author wuZhenFeng
 * @date 2022/11/5 9:16
 */

public interface EmployeeRepository extends BaseRepository<Employee, Long> {
}
