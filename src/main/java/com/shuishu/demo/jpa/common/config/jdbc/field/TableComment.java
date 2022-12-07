package com.shuishu.demo.jpa.common.config.jdbc.field;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.common.collect.Lists;
import com.shuishu.demo.jpa.common.config.domain.BasePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ：shuishu
 * @date   ：2022/3/22 14:19
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Component
@ConditionalOnProperty(prefix = "ss", name = "init", havingValue = "true")
public class TableComment {
    private static final Log log = LogFactory.get();
    @Resource
    private DBManager dbManager;

    public void createComment() throws Exception {
        Map<String, String> beanWithAnnotation = FieldUtil.getAllClass();
        Set<Map.Entry<String, String>> entitySet = beanWithAnnotation.entrySet();
        for (Map.Entry<String, String> entry : entitySet) {
            String tableName = entry.getKey();
            String value = entry.getValue();
            Class<?> clazz = Class.forName(value);
            StringBuilder sql = new StringBuilder();

            ApiModel apiModel = clazz.getAnnotation(ApiModel.class);
            if (apiModel != null) {
                sql.append("comment on table ").append(tableName).append(" is '").append(apiModel.description()).append("';");
            }

            Field[] fs = clazz.getDeclaredFields();
            List<Method> methods = Lists.newArrayList(clazz.getDeclaredMethods());
            for (Field f : fs) {
                if ("serialVersionUID".equals(f.getName())) {
                    continue;
                }
                Transient aTransient = f.getDeclaredAnnotation(Transient.class);
                if (aTransient != null) {
                    continue;
                }
                ApiModelProperty msg = f.getDeclaredAnnotation(ApiModelProperty.class);
                Column column = f.getDeclaredAnnotation(Column.class);
                if (msg != null) {
                    String columnName = FieldUtil.humpToLine2(f.getName());
                    if (column == null) {
                        column = getMethodAnnotation(column, methods, f.getName());
                    }
                    if (column != null && StringUtils.hasText(column.name())) {
                        columnName = column.name();
                    }
                    sql.append(" comment on column ").append(tableName).append(".").append(columnName).append(" is '").append(msg.value().replace("'", " ")).append("';");
                }
            }
            if (StringUtils.hasText(sql)) {
                if (BasePO.class.isAssignableFrom(clazz)) {
                    sql.append(" comment on column ").append(tableName).append(".create_time is '创建时间';")
                            .append(" comment on column ").append(tableName).append(".create_user_id is '创建人id';")
                            .append(" comment on column ").append(tableName).append(".update_time is '修改时间';")
                            .append(" comment on column ").append(tableName).append(".update_user_id is '修改人id';");
                }
            }
            dbManager.executeSql(sql.toString());
        }
        dbManager.closeAll(null, dbManager.getConnection());
    }

    private Column getMethodAnnotation(Column column, List<Method> methods, String columnName) {
        // 解决不规范的get方法，如：boolean isxxx(),boolean getIsxxx()
        String columnName0 = columnName;
        columnName = columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
        String columnName1 = "get" + columnName;
        String columnName2 = "is" + columnName;
        String columnName3 = "getIs" + columnName;

        Iterator<Method> iterator = methods.iterator();
        while (iterator.hasNext()) {
            Method method = iterator.next();
            String methodName = method.getName();
            if (methodName.equals(columnName1) || methodName.equals(columnName2) || methodName.equals(columnName3) ||
                    methodName.equals(columnName0)) {
                column = method.getDeclaredAnnotation(Column.class);
                iterator.remove();
                return column;
            }
        }
        return null;
    }

    /**
     * 启动加载
     */
    @Component
    @ConditionalOnProperty(prefix = "cslinks", name = "init", havingValue = "true")
    @Order(value = 3)
    static class TableCommentInit implements CommandLineRunner {

        @Resource
        private TableComment tableComment;

        @Override
        public void run(String... args) throws Exception {
            tableComment.createComment();
            log.error("初始化表注释完成");
        }
    }
}
