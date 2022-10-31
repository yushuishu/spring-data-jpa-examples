package com.shuishu.demo.jpa.common.config.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseVO is a Querydsl query type for BaseVO
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseVO extends EntityPathBase<BaseVO<? extends BasePO>> {

    private static final long serialVersionUID = -853344495L;

    public static final QBaseVO baseVO = new QBaseVO("baseVO");

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QBaseVO(String variable) {
        super((Class) BaseVO.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QBaseVO(Path<? extends BaseVO> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QBaseVO(PathMetadata metadata) {
        super((Class) BaseVO.class, metadata);
    }

}

