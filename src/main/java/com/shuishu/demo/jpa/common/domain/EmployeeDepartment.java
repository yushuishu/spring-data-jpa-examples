package com.shuishu.demo.jpa.common.domain;


import com.shuishu.demo.jpa.common.config.domain.BasePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author wuZhenFeng
 * @date 2022/11/5 9:09
 */
@Setter
@Getter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(description = "员工部门关联表")
@Table(name = "ss_employee_department")
public class EmployeeDepartment extends BasePO {

    @Id
    @GeneratedValue(generator = "CustomIdGenerator")
    @GenericGenerator(name = "CustomIdGenerator", strategy = "com.shuishu.demo.jpa.common.config.id.CustomIdGenerator")
    @ApiModelProperty(value = "员工部门Id")
    private Long employeeDepartmentId;

    @ApiModelProperty(value = "员工Id")
    private Long employeeId;

    @ApiModelProperty(value = "部门Id")
    private Long departmentId;

    private Long positionId;
}
