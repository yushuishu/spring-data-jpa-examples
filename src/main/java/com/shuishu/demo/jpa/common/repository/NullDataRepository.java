package com.shuishu.demo.jpa.common.repository;


import com.shuishu.demo.jpa.common.config.jdbc.BaseRepository;
import com.shuishu.demo.jpa.common.domain.po.NullData;

/**
 * @author ：shuishu
 * @date   ：2022/11/17 9:39
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */

public interface NullDataRepository extends BaseRepository<NullData, Long> {
    NullData findFirstByDataId(Long dataId);

}
