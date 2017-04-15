package com.yewei.app.server.framework;

import com.yewei.app.server.framework.exception.EngineException;
import com.yewei.app.server.framework.exception.ExcepFactor;
import com.yewei.app.server.framework.type.RequestFrom;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求上下文
 */
@Data
public class RequestContext {

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 请求时间
     */
    private Date requestTime;

    /**
     * HTTP请求方法
     */
    private String method;

    /**
     * 请求URI地址
     */
    private String uri;

    /**
     * API签名
     */
    private String apiSignature;

    /**
     * 请求参数
     */
    private String urlParameter;

    /**
     * 请求内容
     */
    private String requestParam;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 客户端应用ID
     */
    private Long appId;

    /**
     * 用户ID
     */
    private Long userId;


    /**
     * 用户ip,如果是内网服务器端调用，该ip是调用方通过 Api-RemoteIP机制传递的用户ip
     */
    private String remoteHost;

    /**
     * 请求来源信息：INNER(内网), OUTER(外网)
     */
    private RequestFrom requestFrom;

    /**
     * 本地IP
     */
    private String localHost;

    /**
     * 是否是官方APP
     */
    private boolean isOfficialApp;

    /**
     * 客户端平台
     */
    private String platform;

    /**
     * 客户端版本
     */
    private ClientVersion clientVersion = ClientVersion.NULL;


    /**
     * 应答时间
     */
    private Date responseTime;

    /**
     * HTTP应答状态码
     */
    private int responseStatus;

    /**
     * 应答内容
     */
    private String responseParam;


    /**
     * 接口响应使用时间，单位：毫秒
     */
    private long useTime;

    /**
     * 属性
     */
    private Map<String, Object> attribute = new HashMap<>();

    /**
     * 原始请求信息
     */
    private HttpServletRequest originRequest;

    /**
     * 异常因素
     */
    private ExcepFactor excepFactor;

    @Override
    public String toString() {
        return "RequestContext{" +
                "requestId='" + requestId + '\'' +
                ", requestTime=" + requestTime +
                ", method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", urlParameter='" + urlParameter + '\'' +
                ", requestParam='" + requestParam + '\'' +
                ", appId=" + appId +
                ", userId=" + userId +
                ", remoteHost='" + remoteHost + '\'' +
                ", localHost='" + localHost + '\'' +
                ", responseTime=" + responseTime +
                ", responseStatus=" + responseStatus +
                ", responseParam='" + responseParam + '\'' +
                ", useTime=" + useTime +
                '}';
    }
}
