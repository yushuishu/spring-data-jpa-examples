package com.shuishu.demo.jpa.common.dsl;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.shuishu.demo.jpa.common.config.domain.PageDTO;
import com.shuishu.demo.jpa.common.config.domain.PageVO;
import com.shuishu.demo.jpa.common.config.jdbc.BaseDsl;
import com.shuishu.demo.jpa.common.domain.po.Order;
import com.shuishu.demo.jpa.common.domain.po.QOrder;
import com.shuishu.demo.jpa.common.domain.po.QProduct;
import com.shuishu.demo.jpa.common.domain.vo.ProductVO;
import com.shuishu.demo.jpa.common.domain.vo.UserVO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author ：shuishu
 * @date   ：2022/11/27 16:21
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */
@Component
public class ProductDsl extends BaseDsl {
    private QProduct qProduct = QProduct.product;
    private QOrder qOrder = QOrder.order;


    /**
     * queryDsl5.0 版本，hibernate6.x以上，要使用transform()，需要注意配置的JPAQueryFactory
     * <a href="https://github.com/querydsl/querydsl/issues/3428">...</a>
     * -------------------------------------------------------------------------------------
     *  @Bean
     *  public JPAQueryFactory jpaQueryFactory() {
     *      return new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
     *  }
     * -------------------------------------------------------------------------------------
     *
     * 一对多查询，qProduct主表  qOrder从表
     * 使用 innerJoin() 产生笛卡尔积，以 qProduct分组，将 qOrder结果转换到 .as("orderList") 属性中。
     * 尝试调用接口，查看返回结果的数据格式结构，可以很方便的处理数据集，不必要在 service 层使用stream等方式处理结果集
     *
     * transform() ：
     * GroupBy.groupBy(qProduct) ：以。。。分组，可以是字段 qProduct.productId ，也可以是整个实体 qProduct
     * GroupBy.list() ：从表数据集
     *
     * @return -
     */
    public List<ProductVO> findOneToManyList() {
        return jpaQueryFactory.from(qProduct).innerJoin(qOrder).on(qProduct.productId.eq(qOrder.productId))
                .transform(GroupBy.groupBy(qProduct).list((
                        Projections.fields(ProductVO.class,
                                qProduct.productId,
                                qProduct.productName,
                                qProduct.productDescription,
                                qProduct.productCount,
                                qProduct.productPrice,
                                qProduct.specification,
                                GroupBy.list(Projections.fields(Order.class,
                                        qOrder.orderId,
                                        qOrder.uid,
                                        qOrder.productId,
                                        qOrder.userName,
                                        qOrder.productName,
                                        qOrder.price,
                                        qOrder.count,
                                        qOrder.status
                                )).as("orderList")))
                ));
    }

    /**
     * queryDsl5.0 版本，hibernate6.x以上，要使用transform()，需要注意配置的JPAQueryFactory
     * <a href="https://github.com/querydsl/querydsl/issues/3428">...</a>
     * -------------------------------------------------------------------------------------
     *  @Bean
     *  public JPAQueryFactory jpaQueryFactory() {
     *      return new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
     *  }
     * -------------------------------------------------------------------------------------
     *
     * 一对多查询，qProduct主表  qOrder从表
     *
     * 一对多关系表，分页查询，会存在笛卡尔积，所以查询总数的时候，以主表id分组去重，获取总数
     *
     * @param userName -
     * @param productCount -
     * @param pageDTO -
     * @return -
     */
    public PageVO<ProductVO> findOneToManyConditionPage(String userName, Integer productCount, PageDTO pageDTO) {
        PageVO<ProductVO> page = pageDTO.toPageVO(ProductVO.class);
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(userName)){
            builder.and(qOrder.userName.eq(userName));
        }
        if (productCount != null){
            builder.and(qProduct.productCount.eq(productCount));
        }

        // groupBy: 一对多关系表，分页查询，会存在笛卡尔积，所以查询总数的时候，以主表id分组去重，获取总数
        // 需要注意builder的条件都是qProduct主表的字段条件
        long count = jpaQueryFactory.select(qProduct.productId).from(qProduct).innerJoin(qOrder).on(qProduct.productId.eq(qOrder.productId))
                .where(builder).groupBy(qProduct.productId).distinct().fetchCount();
        page.setTotalElements(count);

        List<ProductVO> resultList = jpaQueryFactory.from(qProduct).leftJoin(qOrder).on(qProduct.productId.eq(qOrder.productId))
                .where(builder).offset(page.getOffset()).limit(page.getPageSize())
                .transform(GroupBy.groupBy(qProduct).list((
                        Projections.fields(ProductVO.class,
                                qProduct.productId,
                                qProduct.productName,
                                qProduct.productDescription,
                                qProduct.productCount,
                                qProduct.productPrice,
                                qProduct.specification,
                                GroupBy.list(Projections.fields(Order.class,
                                        qOrder.orderId,
                                        qOrder.uid,
                                        qOrder.productId,
                                        qOrder.userName,
                                        qOrder.productName,
                                        qOrder.price,
                                        qOrder.count,
                                        qOrder.status
                                )).as("orderList")))
                ));
        page.setDataList(resultList);

        return page;
    }

}
