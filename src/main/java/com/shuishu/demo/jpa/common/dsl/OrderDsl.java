package com.shuishu.demo.jpa.common.dsl;


import com.shuishu.demo.jpa.common.config.jdbc.BaseDsl;
import com.shuishu.demo.jpa.common.domain.po.QOrder;
import com.shuishu.demo.jpa.common.domain.po.QProduct;
import org.springframework.stereotype.Component;

/**
 * @author ：shuishu
 * @date   ：2022/11/27 16:21
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */
@Component
public class OrderDsl extends BaseDsl {
    private QOrder qOrder = QOrder.order;
    private QProduct qProduct = QProduct.product;

}
