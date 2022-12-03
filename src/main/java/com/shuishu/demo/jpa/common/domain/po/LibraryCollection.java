package com.shuishu.demo.jpa.common.domain.po;


import com.shuishu.demo.jpa.common.config.domain.BasePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author wuZhenFeng
 * @date 2022/12/3 11:32
 */
@Setter
@Getter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(description = "图书馆藏表")
@Table(name = "ss_library_collection")
public class LibraryCollection extends BasePO {
    @Id
    @GeneratedValue(generator = "CustomIdGenerator")
    @GenericGenerator(name = "CustomIdGenerator", strategy = "com.shuishu.demo.jpa.common.config.id.CustomIdGenerator")
    @ApiModelProperty(value = "馆藏Id")
    private Long libraryCollectionId;

    @ApiModelProperty(value = "索书号")
    private String bookCallNumber;

    @ApiModelProperty(value = "图书标题")
    private String bookTitle;

}
