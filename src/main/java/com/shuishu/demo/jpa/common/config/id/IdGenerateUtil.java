package com.shuishu.demo.jpa.common.config.id;


import com.alibaba.fastjson.JSONObject;

/**
 * @author ：shuishu
 * @date   ：2022/3/22 14:12
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */

public class IdGenerateUtil {
    private IdGenerateUtil() {
    }

    private final IdGenerate idGenerate = new IdGenerate(0, 0);

    private static class SingletonInstance {
        private static final IdGenerateUtil INSTANCE = new IdGenerateUtil();
    }

    public static IdGenerateUtil getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public long getId() {
        return idGenerate.nextId();
    }

    public long getId(long workerId) {
        return idGenerate.nextId(workerId);
    }

    public long getIdWithTimestamp(long timestamp) {
        return idGenerate.nextIdWithTimestamp(timestamp);
    }

    public JSONObject getIdInfo(long id) {
        return idGenerate.parseInfo(id);
    }
}
