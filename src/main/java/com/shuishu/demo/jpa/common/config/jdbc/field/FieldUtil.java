package com.shuishu.demo.jpa.common.config.jdbc.field;


import org.reflections.Reflections;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shuishu
 * @date 2022/3/22 14:18
 */

public class FieldUtil {
    public static Map<String, String> getAllClass() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        Reflections reflections = new Reflections("com.tuodi.cslinks.common");
        Set<Class<?>> claSet = reflections.getTypesAnnotatedWith(Table.class);
        for (Class<?> cla : claSet) {
            String tableName = getTableName(cla);
            if (StringUtils.hasText(tableName)){
                map.put(tableName, cla.getName());
            }
        }
        return map;
    }


    public static String getTableName(Class<? extends Object> cla) throws Exception {
        Table table = (Table) cla.getAnnotation(Table.class);

        if (table == null){
            table = (Table) cla.getAnnotation(org.hibernate.annotations.Table.class);
        }
        String tableName = "";
        if (table == null) {
            Entity entity = (Entity) cla.getAnnotation(Entity.class);
            if (entity != null) {
                tableName = humpToLine2(cla.getSimpleName());
            }
        } else {
            tableName = table.name();
        }
        return tableName;
    }

    static Pattern humpPattern = Pattern.compile("[A-Z]");

    public static String humpToLine2(String str) throws Exception {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        if (sb.indexOf("_") == 0) {
            return sb.substring(1, sb.length());
        }
        return sb.toString();
    }
}
