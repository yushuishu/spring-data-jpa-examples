package com.shuishu.demo.jpa.common.domain.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shuishu.demo.jpa.common.config.domain.BaseVO;
import com.shuishu.demo.jpa.common.config.swagger.ApiRequestInclude;
import com.shuishu.demo.jpa.common.domain.dto.LibraryCollectionDTO;
import com.shuishu.demo.jpa.common.domain.po.LibraryCollection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author wuZhenFeng
 * @date 2022/12/3 13:12
 */
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LibraryCollectionVO extends BaseVO<LibraryCollection> {

    @ApiModelProperty(value = "图书分类")
    private String classifyName;

    @ApiModelProperty(value = "馆藏数量")
    private Long libraryCollectionCount;

    @ApiModelProperty(value = "馆藏Id")
    private Long libraryCollectionId;

    @ApiModelProperty(value = "索书号")
    private String bookCallNumber;

    @ApiModelProperty(value = "图书标题")
    private String bookTitle;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String dateStr;

}
