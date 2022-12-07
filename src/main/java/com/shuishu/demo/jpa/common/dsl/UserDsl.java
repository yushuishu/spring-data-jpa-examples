package com.shuishu.demo.jpa.common.dsl;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.shuishu.demo.jpa.common.config.jdbc.BaseDsl;
import com.shuishu.demo.jpa.common.domain.dto.UserDTO;
import com.shuishu.demo.jpa.common.domain.po.QAddress;
import com.shuishu.demo.jpa.common.domain.po.QUser;
import com.shuishu.demo.jpa.common.domain.po.User;
import com.shuishu.demo.jpa.common.domain.vo.UserVO;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author ：shuishu
 * @date   ：2022/10/29 17:22
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */
@Component
public class UserDsl extends BaseDsl {
    private QUser qUser = QUser.user;
    private QAddress qAddress = QAddress.address;

    /**
     * Projections.fields() ：通过反射映射结果集，所以对象属性可以没有set方法
     *      - Projections.bean() ：1、对象的属性必须有set方法 2、如果封装类型加了注解 @Accessors(chain = true) 就只能使用 fields() 来映射
     *      - Projections.constructor() ：构造函数
     * 使用方式最多的是 fields()
     *
     * eq() ：等于
     * ne() ：不等于
     * gt   ：大于
     * goe  ：大于等于
     * lt   ：小于
     * loe  ：小于等于
     *
     * fetch() ：获取结果集
     * @param userAge -年龄
     * @return -
     */
    public List<UserVO> findUseEqList(Integer userAge) {
        return jpaQueryFactory.select(Projections.fields(UserVO.class,
                qUser.userId,
                qUser.userName,
                qUser.userAge,
                qUser.integrate
        ))
                .from(qUser)
                .where(qUser.userAge.eq(userAge))
                .fetch();
    }

    /**
     * selectFrom() ：封装的结果就是 PO 对象，所以不需要 Projections.fields()
     * fetchFirst() ：查询获取一个值，（存在多条数据），也就是 limit(1).fetchOne()
     * fetchOne()   ：查询获取一个值，如果存在多个值，执行SQL异常。只有在非常明确数据只有一条的情况会使用，就比如此方法查询使用 用户id（主键唯一）
     * @param userId -用户id
     * @return -
     */
    public User findByUserId(Long userId) {
        //return jpaQueryFactory.selectFrom(qUser).where(qUser.userId.eq(userId)).fetchFirst();
        return jpaQueryFactory.selectFrom(qUser).where(qUser.userId.eq(userId)).fetchOne();
    }

    /**
     * BooleanBuilder() ：封装条件查询。1、简化条件查询。2、逻辑判断（比如空）
     *      - builder.and() ：AND
     *      - builder.or()  ：OR
     * @param userDTO -
     * @return -
     */
    public List<UserVO> findUseBooleanBuilderList(UserDTO userDTO) {
        // com.querydsl.core
        BooleanBuilder builder = new BooleanBuilder();
        if (userDTO.getUserId() != null){
            builder.and(qUser.userAge.eq(userDTO.getUserAge()));
        }
        if (userDTO.getIntegrate() != null){
            builder.and(qUser.integrate.eq(userDTO.getIntegrate()));
        }
        return jpaQueryFactory.select(Projections.fields(UserVO.class,
                qUser.userId,
                qUser.userAge,
                qUser.userName,
                qUser.integrate
        ))
                .from(qUser)
                .where(builder)
                .fetch();
    }

    /**
     * startsWith() 字段值以...开始
     * @param userName -
     * @return -
     */
    public List<UserVO> findUseStartsWith(String userName) {
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(userName)){
            builder.and(qUser.userName.startsWith(userName));
        }
        return jpaQueryFactory.select(Projections.fields(UserVO.class,
                qUser.userId, qUser.userName, qUser.userAge, qUser.integrate
        ))
                .from(qUser)
                .where(builder)
                .orderBy(qUser.userAge.asc().nullsFirst())
                .fetch();
    }

    /**
     * SQL中的语法IN 的使用
     * @param userAgeList  -
     * @return -
     */
    public List<UserVO> findUseInList(List<Integer> userAgeList) {
        BooleanBuilder builder = new BooleanBuilder();
        if (!ObjectUtils.isEmpty(userAgeList)){
            builder.and(qUser.userAge.in(userAgeList));
        }
        return jpaQueryFactory.select(Projections.fields(UserVO.class,
                qUser.userId, qUser.userName, qUser.userAge, qUser.integrate
        ))
                .from(qUser)
                .where(builder)
                .fetch();
    }

    /**
     * DATE_FORMAT 自定义格式
     * @return -
     */
    public List<UserVO> findUseDateFormatList() {
        return jpaQueryFactory.select(Projections.fields(UserVO.class,
                qUser.userId,
                qUser.userName,
                qUser.userAge,
                qUser.integrate,
                Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d')", qUser.createTime)
        ))
                .from(qUser)
                .fetch();
    }

    /**
     * avg() 平均值
     * @return -
     */
    public Double findUseAvg() {
        return jpaQueryFactory.select(qUser.userAge.avg()).from(qUser).fetchOne();
    }

    /**
     * sum() 总和
     * @return -
     */
    public Integer findUseSum() {
        return jpaQueryFactory.select(qUser.integrate.sum()).from(qUser).fetchOne();
    }

    /**
     * 合并多个字段字符串，如果是数字使用 stringValue()
     * @return -
     */
    public List<UserVO> findUseConcatList() {
        return jpaQueryFactory.select(Projections.fields(UserVO.class,
                qUser.userName,
                qUser.userAge,
                qUser.userName.concat(":").concat(qUser.userAge.stringValue()).as("nameConcatAge")
        ))
                .from(qUser)
                .fetch();
    }

    /**
     * 指定字段包含指定值的条件
     * contains ： 包含
     * containsIgnoreCase ：不区分大小写
     * @param userName -
     * @return -
     */
    public List<UserVO> findUseContainsList(String userName) {
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(userName)){
            builder.and(qUser.userName.contains(userName));
        }
        return jpaQueryFactory.select(Projections.fields(UserVO.class,
                qUser.userId,
                qUser.userName,
                qUser.userAge
        ))
                .from(qUser)
                .where(builder)
                .fetch();
    }

    /**
     * count()   总数
     * groupBy() 分组
     * @return -
     */
    public List<UserVO> findUseGroupByList() {
        return jpaQueryFactory.select(Projections.fields(UserVO.class,
                qUser.userAge,
                qUser.userId.count().as("userCount")
        ))
                .from(qUser)
                .groupBy(qUser.userAge)
                .fetch();
    }

    /**
     * leftJoin().on()  ：左连接
     * rightJoin().on() ：右连接
     * innerJoin().on() ：内连接
     * 多表关联查询
     * @return -
     */
    public List<UserVO> findMultiTableJoinList() {
        return jpaQueryFactory.select(Projections.fields(UserVO.class,
                qUser.userId,
                qUser.userName,
                qUser.userName,
                qUser.integrate,
                qAddress.addressId,
                qAddress.addressDescription
        ))
                .from(qUser)
                .leftJoin(qAddress).on(qUser.addressId.eq(qAddress.addressId))
                .fetch();
    }


    public void updateIntegrate(Long userId, int integrate){
        if (userId != null){
            jpaQueryFactory.update(qUser)
                    .set(qUser.integrate, qUser.integrate.add(integrate))
                    .where(qUser.userId.eq(userId))
                    .execute();
        }
    }



}
