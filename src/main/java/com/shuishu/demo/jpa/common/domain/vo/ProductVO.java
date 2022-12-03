package com.shuishu.demo.jpa.common.domain.vo;


import com.shuishu.demo.jpa.common.config.domain.BaseVO;
import com.shuishu.demo.jpa.common.domain.po.Order;
import com.shuishu.demo.jpa.common.domain.po.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author wuZhenFeng
 * @date 2022/11/27 16:30
 */
@Setter
@Getter
@ToString
public class ProductVO extends BaseVO<Product> {
    @ApiModelProperty(value = "产品Id")
    private Long productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "产品描述")
    private String productDescription;

    @ApiModelProperty(value = "库存")
    private Integer productCount;

    @ApiModelProperty(value = "产品价格")
    private Double productPrice;

    @ApiModelProperty(value = "产品规格")
    private String specification;

    @ApiModelProperty(value = "订单list")
    private List<Order> orderList;

}
