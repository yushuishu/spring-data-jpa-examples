package com.shuishu.demo.jpa.common.domain.po;


/**
 * @author wuZhenFeng
 * @date 2022/11/27 16:09
 */

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
 * @date   ：2022/11/27 16:13
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
@ApiModel(description = "订单表")
@Table(name = "ss_order")
public class Order extends BasePO {
    @Id
    @GeneratedValue(generator = "CustomIdGenerator")
    @GenericGenerator(name = "CustomIdGenerator", strategy = "com.shuishu.demo.jpa.common.config.id.CustomIdGenerator")
    @ApiModelProperty(value = "订单Id")
    private Long orderId;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "价格")
    private Double price;

    @ApiModelProperty(value = "数量")
    private Long count;

    @ApiModelProperty(value = "支付状态：0：初始，1：已支付，2：待支付，3：已取消")
    private Integer status;

}
