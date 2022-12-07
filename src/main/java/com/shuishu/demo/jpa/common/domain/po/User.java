package com.shuishu.demo.jpa.common.domain.po;


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
 * @author ：shuishu
 * @date   ：2022/10/29 17:20
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */
@Setter
@Getter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(description = "用户信息")
@Table(name = "ss_user")
public class User extends BasePO {

    @Id
    @GeneratedValue(generator = "CustomIdGenerator")
    @GenericGenerator(name = "CustomIdGenerator", strategy = "com.shuishu.demo.jpa.common.config.id.CustomIdGenerator")
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

}
