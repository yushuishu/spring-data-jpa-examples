package com.shuishu.demo.jpa.common.config.domain;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.QueryResults;
import com.shuishu.demo.jpa.common.config.swagger.ApiRequestInclude;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ：shuishu
 * @date   ：2022/3/22 14:38
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageVO<T> implements Serializable {
    private static final Log log = LogFactory.get();

    public interface PageInfo {
    }
    private List<T> dataList = new ArrayList<>();

    @ApiRequestInclude(groups = PageInfo.class)
    @Min(value = 1, message = "每页数目从1开始")
    @ApiModelProperty(value = "每页数量", example = "5")
    private long pageSize = 5;

    @ApiRequestInclude(groups = PageInfo.class)
    @Min(value = 1, message = "页码从1开始")
    @ApiModelProperty(value = "页码", example = "1")
    private long pageNumber = 1;

    private Long offset;

    @ApiModelProperty(value = "总页数")
    private long totalPages;

    @ApiModelProperty(value = "总数量")
    private long totalElements;


    public PageVO() {
        super();
    }

    public PageVO(long pageSize, long pageNumber) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }


    public PageVO(List<T> data, long pageSize, long pageNumber, long totalElements) {
        this.dataList = data;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalElements = totalElements;
        this.totalPages = (totalElements % pageSize == 0) ? (totalElements / pageSize) : (totalElements / pageSize) + 1;
    }

    public static PageVO of(List data, long pageSize, long pageNumber, long totalElements) {
        return new PageVO(data, pageSize, pageNumber, totalElements);
    }

    public static PageVO of(List data, Pageable pageable, long totalElements) {
        return new PageVO(data, pageable.getPageSize(), pageable.getPageNumber(), totalElements);
    }

    public static PageVO ofEmptyPage(PageVO page) {

        PageVO<Object> emptyPage = new PageVO<>();
        emptyPage.setDataList(Collections.emptyList());
        emptyPage.setPageNumber(page.getPageNumber());
        emptyPage.setPageSize(page.getPageSize());
        return emptyPage;
    }

    public PageVO updatePage(List data) {
        this.dataList = data;
        return this;
    }

    public PageVO updatePage(List data, long count) {
        this.dataList = data;
        setTotalElements(count);
        return this;
    }

    public long getOffset() {
        if (offset != null) {
            return offset.longValue();
        }
        return (pageNumber - 1) * pageSize;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

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

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        if (pageSize > 0) {
            log.info(">>>>>>>>>>>>>>>>>>" + totalElements % pageSize);
            this.totalPages = (totalElements % pageSize == 0) ? (totalElements / pageSize) : (totalElements / pageSize) + 1;
        }
        log.info(">>>>>>>>>>>>>>>>>>" + this.totalPages);
        this.totalElements = totalElements;
    }

    //转换dsl的查询结果
    public PageVO<T> tranferDsl(PageVO page, QueryResults<T> results) {
        page.setOffset(results.getOffset());
        page.setDataList(results.getResults());
        page.setTotalElements(results.getTotal());
        //总页数
        long pageSize = page.getPageSize();
        long totalElements = page.getTotalElements();
        long totalPage = (totalElements % pageSize == 0) ? (totalElements / pageSize) : (totalElements / pageSize + 1);
        page.setTotalPages(totalPage);
        return page;
    }

    @Override
    public String toString() {
        return "Page [dataList=" + dataList + ", pageSize=" + pageSize
                + ", pageNumber=" + pageNumber + ", offset=" + offset
                + ", totalPages=" + totalPages + ", totalElements="
                + totalElements + "]";
    }
}
