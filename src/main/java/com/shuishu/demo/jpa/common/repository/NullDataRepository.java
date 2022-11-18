package com.shuishu.demo.jpa.common.repository;


import com.shuishu.demo.jpa.common.config.jdbc.BaseRepository;
import com.shuishu.demo.jpa.common.domain.NullData;

/**
 * @author wuZhenFeng
 * @date 2022/11/17 9:39
 */

public interface NullDataRepository extends BaseRepository<NullData, Long> {
    NullData findFirstByDataId(Long dataId);

}
