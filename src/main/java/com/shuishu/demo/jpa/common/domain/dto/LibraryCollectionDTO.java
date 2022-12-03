package com.shuishu.demo.jpa.common.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.shuishu.demo.jpa.common.config.domain.BaseDTO;
import com.shuishu.demo.jpa.common.config.swagger.ApiRequestInclude;
import com.shuishu.demo.jpa.common.domain.po.LibraryCollection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author wuZhenFeng
 * @date 2022/12/3 14:02
 */
@Setter
@Getter
@ToString
public class LibraryCollectionDTO extends BaseDTO<LibraryCollection> {
    public interface FindUseLikePage{}
    public interface FindUseBetween{}
    public interface FindStringTemplateUseList{}
    ;

    @ApiModelProperty(value = "馆藏Id")
    private Long libraryCollectionId;

    @ApiModelProperty(value = "索书号")
    private String bookCallNumber;

    @ApiRequestInclude(groups = {FindUseLikePage.class})
    @ApiModelProperty(value = "图书标题")
    private String bookTitle;

    @ApiRequestInclude(groups = {FindStringTemplateUseList.class})
    @ApiModelProperty(value = "时间格式", example = "yyyy-MM-dd")
    private String pattern;

    @ApiRequestInclude(groups = {FindUseLikePage.class, FindStringTemplateUseList.class, FindUseBetween.class})
    @NotNull(message = "开始时间不能为空！", groups = {FindUseBetween.class})
    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @ApiRequestInclude(groups = {FindUseLikePage.class, FindStringTemplateUseList.class, FindUseBetween.class})
    @NotNull(message = "结束时间不能为空！", groups = {FindUseBetween.class})
    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}
