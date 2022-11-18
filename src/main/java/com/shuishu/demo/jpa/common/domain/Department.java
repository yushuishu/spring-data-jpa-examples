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
 * @date 2022/11/4 17:57
 */
@Setter
@Getter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(description = "部门表")
@Table(name = "ss_department")
public class Department extends BasePO {
    @Id
    @GeneratedValue(generator = "CustomIdGenerator")
    @GenericGenerator(name = "CustomIdGenerator", strategy = "com.shuishu.demo.jpa.common.config.id.CustomIdGenerator")
    @ApiModelProperty(value = "部门Id")
    private Long departmentId;

    @Column(nullable = false, unique = true)
    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @Column(nullable = false, unique = true)
    @ApiModelProperty(value = "部门编号")
    private String departmentCode;

    @ApiModelProperty(value = "部门描述")
    private String departmentDescription;
}
