package com.shuishu.demo.jpa.common.domain.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.shuishu.demo.jpa.common.config.domain.BaseVO;
import com.shuishu.demo.jpa.common.domain.po.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author wuZhenFeng
 * @date 2022/12/3 14:29
 */
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVO extends BaseVO<User> {
    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "年龄")
    private Integer userAge;

    @ApiModelProperty(value = "积分")
    private Integer integrate;

    @ApiModelProperty(value = "地址Id")
    private Long addressId;

    @ApiModelProperty(value = "地址描述")
    private Long addressDescription;

    @ApiModelProperty(value = "用户数量")
    private Long userCount;

    @ApiModelProperty(value = "平均年龄（聚合avg）")
    private Double userAgeAvg;

    @ApiModelProperty(value = "总积分 （聚合sum）")
    private Integer integrateSum;

    @ApiModelProperty(value = "姓名:年龄 （聚合concat）")
    private String nameConcatAge;


}
