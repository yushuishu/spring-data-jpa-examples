package com.shuishu.demo.jpa.common.config.domain;


import org.springframework.beans.BeanUtils;

/**
 * @author ：shuishu
 * @date   ：2022/3/22 14:26
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：数据传输对象(Data Transfer Object)，展示层与服务层之间的数据传输对象
 */

public class BaseDTO<T extends BasePO> {
    /**
     * Dto转化为Po，进行后续业务处理
     *
     * @param clazz -
     * @return Po
     */
    public T toPo(Class<T> clazz) {
        T t = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(this, t);
        return t;
    }
}
