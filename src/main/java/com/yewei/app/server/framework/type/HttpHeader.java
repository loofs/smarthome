package com.yewei.app.server.framework.type;

/**
 * Created by lenovo on 2017/4/6.
 * HTTP请求头常量定义
 */
public interface HttpHeader {

    /**
     * 请求ID
     */
    String REQUEST_ID = "X-Engine-RequestID";

    /**
     * 应用ID
     */
    String APP_ID = "X-Engine-APPID";

    /**
     * 用户ID
     */
    String USER_ID = "X-Engine-UID";

    /**
     * 远端IP地址
     */
    String REMOTE_IP = "X-Engine-RemoteIP";

    /**
     * 本地IP地址
     */
    String LOCAL_IP = "X-Engine-IP";

    /**
     * 访问令牌
     */
    String ACCESS_TOKEN = "Access-Token";

    /**
     * 客户端版本
     */
    String CLIENT_VERSION = "X-WVersion";


    String MULTIPART = "multipart/";

    /**
     * 请求来源
     * 用于判断内网外网（Nginx配置添加Header）
     */
    String FROM_HEADER = "X-Engine-From";

    /**
     *
     */
    String SSL_HEADER = "X-Engine-SSL";


}
