package com.shuishu.demo.jpa.common.domain.dto;


import com.shuishu.demo.jpa.common.config.domain.BaseDTO;
import com.shuishu.demo.jpa.common.config.swagger.ApiRequestInclude;
import com.shuishu.demo.jpa.common.domain.po.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author ：shuishu
 * @date   ：2022/12/3 14:34
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */
@Setter
@Getter
@ToString
public class UserDTO extends BaseDTO<User> {
    public interface FindUseInList{}
    public interface FindUseBooleanBuilderList{}
    ;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiRequestInclude(groups = {FindUseBooleanBuilderList.class})
    @ApiModelProperty(value = "年龄")
    private Integer userAge;

    @ApiRequestInclude(groups = {FindUseBooleanBuilderList.class})
    @ApiModelProperty(value = "积分")
    private Integer integrate;

    @ApiRequestInclude(groups = {FindUseInList.class})
    @ApiModelProperty(value = "年龄list")
    private List<Integer> userAgeList;

}
