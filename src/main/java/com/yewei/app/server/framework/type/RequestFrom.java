package com.yewei.app.server.framework.type;

/**
 * Created by lenovo on 2017/4/8.
 * 请求来源类型
 */
public enum RequestFrom {

    // 内网
    INNER,
    // 外网
    OUTER;

    public static final RequestFrom valueOf(String name, RequestFrom defaultValue) {
        if (name == null) {
            return defaultValue;
        }
        try {
            return RequestFrom.valueOf(name.toUpperCase());
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
