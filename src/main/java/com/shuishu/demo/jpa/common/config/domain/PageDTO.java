package com.shuishu.demo.jpa.common.config.domain;



import com.shuishu.demo.jpa.common.config.swagger.ApiRequestInclude;

import javax.validation.constraints.Min;

/**
 * @author shuishu
 * @date 2022/3/22 14:37
 */

public class PageDTO {
    @ApiRequestInclude(groups = PageVO.PageInfo.class)
    @Min(value = 1, message = "每页数目从1开始")
    private long pageSize = 5;

    @ApiRequestInclude(groups = PageVO.PageInfo.class)
    @Min(value = 1, message = "页码从1开始")
    private long pageNumber = 1;

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }
}
