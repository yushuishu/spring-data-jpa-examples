package com.shuishu.demo.jpa.common.dsl;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringTemplate;
import com.shuishu.demo.jpa.common.config.domain.PageDTO;
import com.shuishu.demo.jpa.common.config.domain.PageVO;
import com.shuishu.demo.jpa.common.config.jdbc.BaseDsl;
import com.shuishu.demo.jpa.common.domain.dto.LibraryCollectionDTO;
import com.shuishu.demo.jpa.common.domain.po.QLibraryCollection;
import com.shuishu.demo.jpa.common.domain.vo.LibraryCollectionVO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：shuishu
 * @date   ：2022/12/3 11:36
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */
@Component
public class LibraryCollectionDsl extends BaseDsl {
    private QLibraryCollection qLibraryCollection = QLibraryCollection.libraryCollection;

    /**
     * like("%" + keyword + "%") ：关键字模糊查询
     * orderBy() ：排序
     *      - desc().nullsLast()  ：降序排序，如果为空，就排到最后
     *      - desc().nullsFirst() ：降序排序，如果为空，就排到最前面
     * offset() ：页码
     * limit()  ：一页的数量
     * fetchResults() 获取结果集，里面包含 总数量，没有必要再另外写一个查询总数的SQL语句
     * @param libraryCollectionDTO -
     * @param pageDTO -
     * @return -
     */
    public PageVO<LibraryCollectionVO> findUseLikePage(LibraryCollectionDTO libraryCollectionDTO, PageDTO pageDTO) {
        PageVO<LibraryCollectionVO> page = pageDTO.toPageVO(LibraryCollectionVO.class);
        BooleanBuilder builder = new BooleanBuilder();
        if (libraryCollectionDTO.getStartDate() != null){
            builder.and(qLibraryCollection.createTime.goe(libraryCollectionDTO.getStartDate()));
        }
        if (libraryCollectionDTO.getEndDate() != null){
            builder.and(qLibraryCollection.createTime.loe(libraryCollectionDTO.getEndDate()));
        }
        if (StringUtils.hasText(libraryCollectionDTO.getBookTitle())){
            builder.and(qLibraryCollection.bookTitle.like("%" + libraryCollectionDTO.getBookTitle() + "%"));
        }

        QueryResults<LibraryCollectionVO> results = jpaQueryFactory.select(Projections.fields(LibraryCollectionVO.class,
                qLibraryCollection.libraryCollectionId,
                qLibraryCollection.bookCallNumber,
                qLibraryCollection.bookTitle
        ))
                .from(qLibraryCollection)
                .where(builder)
                .orderBy(qLibraryCollection.createTime.desc().nullsLast())
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .fetchResults();

        page.setTotalElements(results.getTotal());
        page.setDataList(results.getResults());
        return page;
    }


    /**
     *
     * @param startDate -开始时间
     * @param endDate   -结束时间
     * @return -
     */
    public List<LibraryCollectionVO> findUseBetween(Date startDate, Date endDate) {
        return jpaQueryFactory.select(Projections.fields(LibraryCollectionVO.class,
                qLibraryCollection.libraryCollectionId,
                qLibraryCollection.bookCallNumber,
                qLibraryCollection.bookTitle,
                qLibraryCollection.createTime
        ))
                .from(qLibraryCollection)
                .where(qLibraryCollection.createTime.between(startDate, endDate))
                .fetch();
    }

    /**
     * Expressions.stringTemplate() 字符串模板
     * count() 总数
     * @param libraryCollectionDTO -
     * @return -
     */
    public List<LibraryCollectionVO> findStringTemplateUseList(LibraryCollectionDTO libraryCollectionDTO) {
        BooleanBuilder builder = new BooleanBuilder();
        if (libraryCollectionDTO.getStartDate() != null){
            builder.and(qLibraryCollection.updateTime.goe(libraryCollectionDTO.getStartDate()));
        }
        if (libraryCollectionDTO.getEndDate() != null){
            builder.and(qLibraryCollection.updateTime.loe(libraryCollectionDTO.getEndDate()));
        }

        StringTemplate stringTemplate = Expressions.stringTemplate("to_char({0},'" + libraryCollectionDTO.getPattern() + "')", qLibraryCollection.createTime);

        return jpaQueryFactory.select(Projections.fields(LibraryCollectionVO.class,
                stringTemplate.as("dateStr"),
                qLibraryCollection.count().as("libraryCollectionCount")
        ))
                .from(qLibraryCollection)
                .where(builder)
                .groupBy(stringTemplate)
                .orderBy(stringTemplate.asc())
                .fetch();
    }

    /**
     * 查询每个大类的图书数量
     * bookCallNumber： 索书号第一个字母为图书分类的大类
     * @return -
     */
    public Map<String, Long> findStringExpressionUseList() {
        List<String> tempList = new ArrayList<>(Arrays.asList("ABCDEFGHIJKNOPQRSTUVXZabcdefghijknopqrstuvxz".split("")));
        StringExpression stringExpression = new CaseBuilder()
                .when(qLibraryCollection.bookCallNumber.substring(0, 1).in(tempList))
                .then(qLibraryCollection.bookCallNumber.substring(0, 1))
                .otherwise("其它").as("classifyName");

        return jpaQueryFactory.select(Projections.fields(LibraryCollectionVO.class,
                stringExpression,
                qLibraryCollection.count().as("libraryCollectionCount")
        ))
                .from(qLibraryCollection)
                .groupBy(qLibraryCollection.bookCallNumber.substring(0, 1))
                .orderBy(qLibraryCollection.count().desc())
                .fetch()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                LibraryCollectionVO::getClassifyName,
                                Collectors.summingLong(LibraryCollectionVO::getLibraryCollectionCount)
                        )
                );
    }

}
