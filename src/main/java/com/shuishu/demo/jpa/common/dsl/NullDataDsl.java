package com.shuishu.demo.jpa.common.dsl;


import com.shuishu.demo.jpa.common.config.jdbc.BaseDsl;
import com.shuishu.demo.jpa.common.domain.po.NullData;
import com.shuishu.demo.jpa.common.domain.po.QNullData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：shuishu
 * @date   ：2022/11/17 9:40
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */
@Component
public class NullDataDsl extends BaseDsl {
    private QNullData qNullData = QNullData.nullData;


    public List<NullData> findAll() {
        return jpaQueryFactory.selectFrom(qNullData).fetch();
    }

    public NullData findByDataId(long dataId) {
        return jpaQueryFactory.selectFrom(qNullData).where(qNullData.dataId.eq(dataId)).fetchOne();
    }

    public NullData findByDataId2(long dataId) {
        return jpaQueryFactory.selectFrom(qNullData).where(qNullData.dataId.eq(dataId)).fetchFirst();
    }

}
