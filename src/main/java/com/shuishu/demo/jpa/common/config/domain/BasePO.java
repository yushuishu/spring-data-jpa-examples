package com.shuishu.demo.jpa.common.config.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shuishu
 * @date 2022/3/22 14:23
 *
 * 持久化对象(Persistent Object)
 */
@Setter
@Getter
@ToString
@ApiModel
@MappedSuperclass
public class BasePO implements Serializable {
    private static final long serialVersionUID = 6161320035711415441L;
    @CreatedDate
    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @CreatedBy
    @ApiModelProperty(value = "创建人id", hidden = true)
    private Long createUserId;

    @LastModifiedDate
    @ApiModelProperty(value = "修改时间", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @LastModifiedBy
    @ApiModelProperty(value = "修改人id", hidden = true)
    private Long updateUserId;



    public <T extends BaseVO<?>> T toVo(Class<T> clazz) {
        T t = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(this, t);
        return t;
    }
}
