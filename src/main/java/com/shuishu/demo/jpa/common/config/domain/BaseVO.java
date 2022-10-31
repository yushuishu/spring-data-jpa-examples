package com.shuishu.demo.jpa.common.config.domain;


import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author shuishu
 * @date 2022/3/22 14:25
 *
 * 视图对象(View Object), 展示层
 */
@MappedSuperclass
public class BaseVO<T extends BasePO> implements Serializable {
}
