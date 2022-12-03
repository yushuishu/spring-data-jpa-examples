package com.shuishu.demo.jpa.common.dsl;


import com.shuishu.demo.jpa.common.config.jdbc.BaseDsl;
import com.shuishu.demo.jpa.common.domain.po.QOrder;
import com.shuishu.demo.jpa.common.domain.po.QProduct;
import org.springframework.stereotype.Component;

/**
 * @author wuZhenFeng
 * @date 2022/11/27 16:21
 */
@Component
public class OrderDsl extends BaseDsl {
    private QOrder qOrder = QOrder.order;
    private QProduct qProduct = QProduct.product;

}
