package com.yewei.app.server.framework.exception;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

@Getter
public class ExcepFactor implements Serializable {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExcepFactor.class);

    private static final long serialVersionUID = 4826765296261100979L;

    private static final Set<ExcepFactor> excepFactors = new HashSet<>();

    /**
     * HTTP状态码
     */
    private HttpStatus httpStatus;

    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 错误消息（中文）
     */
    private String errorMsgCn;

    private Object[] errerMsgArgs;

    /**
     * 构造方法
     * @param httpStatus
     * @param errorCode
     * @param errorMsg
     * @param errorMsgCn
     */
    protected ExcepFactor(HttpStatus httpStatus, int errorCode, String errorMsg, String errorMsgCn) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorMsgCn = errorMsgCn;
        if (excepFactors.contains(this)) {
            throw new IllegalArgumentException("this error exist: " + this.errorCode);
        }
        excepFactors.add(this);
    }


    // 错误列表
    public static final ExcepFactor E_DEFAULT = new ExcepFactor(
            HttpStatus.INTERNAL_SERVER_ERROR,
            1001,
            "system error!",
            "对不起,服务器内部发生错误,请稍后再试.");

    public static final ExcepFactor E_SERVICE_UNAVAILABLE = new ExcepFactor(
            HttpStatus.SERVICE_UNAVAILABLE,
            1002,
            "service unavailable!",
            "服务端资源不可用!");

    public static final ExcepFactor E_DEPENDENCE_SERVICE_UNAVAILABLE = new ExcepFactor(
            HttpStatus.SERVICE_UNAVAILABLE,
            1003,
            "dependence service unavailable!",
            "依赖服务不可用!");

    public static final ExcepFactor E_SYSTEM_BUSY = new ExcepFactor(
            HttpStatus.SERVICE_UNAVAILABLE,
            1004,
            "system is busy please retry!",
            "系统繁忙，请重试!");

    public static final ExcepFactor E_DIGEST_ERROR = new ExcepFactor(
            HttpStatus.SERVICE_UNAVAILABLE,
            1005,
            "digest error",
            "加解密失败");

    public static final ExcepFactor E_API_NOT_EXIST = new ExcepFactor(
            HttpStatus.NOT_FOUND,
            1010,
            "RequestParam Api not found!",
            "接口不存在!");

    public static final ExcepFactor E_METHOD_ERROR = new ExcepFactor(
            HttpStatus.METHOD_NOT_ALLOWED,
            1011,
            "HTTP METHOD is not suported for this request!",
            "请求的HTTP METHOD不支持!");

    public static final ExcepFactor E_API_DEPRECATED_ERROR = new ExcepFactor(
            HttpStatus.SERVICE_UNAVAILABLE,
            1012,
            "uri is deprecated.",
            "该接口已经废弃");

    public static final ExcepFactor E_UNSUPPORT_MEDIATYPE_ERROR = new ExcepFactor(
            HttpStatus.UNSUPPORTED_MEDIA_TYPE,
            1013,
            "unsupport mediatype (%s)",
            "不支持的 MediaType (%s).");

    public static final ExcepFactor E_PARAM_ERROR = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            1014,
            "param error, see doc for more info.",
            "错误:参数错误，请参考API文档!");

    public static final ExcepFactor E_ILLEGAL_REQUEST = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            1015,
            "Illegal RequestParam!",
            "非法请求！");

    public static final ExcepFactor E_PARAM_MISS_ERROR = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            1016,
            "miss required parameter (%s), see doc for more info.",
            "错误:缺失必选参数:%s，请参考API文档.");

    public static final ExcepFactor E_PARAM_INVALID_ERROR = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            1017,
            "parameter (%s)'s value invalid,expect (%s), but get (%s), see doc for more info.",
            "错误:参数 (%s) 非法,希望得到 (%s),实际得到 (%s)，请参考API文档.");

    public static final ExcepFactor E_POST_BODY_LENGTH_LIMIT = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            1018,
            "request boday length over limit.",
            "请求长度超过限制!");

    public static final ExcepFactor E_INPUT_IMAGEERROR = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            1019,
            "unsupported image type, only suport JPG, GIF, PNG!",
            "不支持的图片类型,仅仅支持JPG,GIF,PNG!");

    public static final ExcepFactor E_INPUT_IMAGESIZEERROR = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            1020,
            "image size too large.",
            "图片太大。");

    public static final ExcepFactor E_FORBID_RESUBMIT = new ExcepFactor(
             HttpStatus.BAD_REQUEST,
            1021,
            "forbid resubmit.",
            "禁止重复提交");

    public static final ExcepFactor E_CLIENT_VERSION_UNSUPPORT = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            1022,
            "client version unsupport this feature.",
            "你的客户端版本不支持该功能，请升级");

    public static final ExcepFactor E_IP_LIMIT = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            1030,
            "IP limit!",
            "IP限制，不能请求该资源!");

    public static final ExcepFactor E_SOURCE_LEVEL_ERROR = new ExcepFactor(
           HttpStatus.FORBIDDEN,
            1031,
            "permission denied! Need a high level appkey!",
            "该资源需要appkey拥有更高级的授权!");

    public static final ExcepFactor E_CLIENTID_ERROR = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            1032,
            "Client not exists!",
            "Client不存在！");


    public static final ExcepFactor E_IP_OUTOFLIMIT = new ExcepFactor(
            HttpStatus.FORBIDDEN,
            1033,
            "IP requests out of rate limit!",
            "IP请求超过上限!");


    public static final ExcepFactor E_USER_OUTOFLIMIT = new ExcepFactor(
            HttpStatus.FORBIDDEN,
            1034,
            "User requests out of rate limit!",
            "用户请求超过上限!");


    public static final ExcepFactor E_API_OUTOFLIMIT = new ExcepFactor(
            HttpStatus.FORBIDDEN,
            1035,
            "User requests for %s out of rate limit!",
            "用户请求接口%s超过上限!");

    public static final ExcepFactor E_SMS_OUTOFLIMIT = new ExcepFactor(
            HttpStatus.FORBIDDEN,
            1036,
            "This phone number requests for verification code out of rate limit! Please try later!",
            "该手机号发送短信超过上限，请稍后再试!");

    public static final ExcepFactor E_USER_NOTOPEN = new ExcepFactor(
            HttpStatus.FORBIDDEN,
            1050,
            "invalid user!",
            "不合法的用户!");

    public static final ExcepFactor E_ENTITY_NOT_FOUND = new ExcepFactor(
            HttpStatus.NOT_FOUND,
            1051,
            "target entity not find.",
            "所查询的对象不存在");

    public static final ExcepFactor E_FORBIDWORD = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            1052,
            "content contain forbid word",
            "内容中包含封禁词汇");

    public static final ExcepFactor E_FORBID_OP = new ExcepFactor(
            HttpStatus.FORBIDDEN,
            1053,
            "forbid this operation.",
            "操作被禁止，请联系微米客服");

    public static final ExcepFactor SERVICE_IS_MAINTAIN = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            1054,
            "service is maintain",
            "很抱歉，此功能正在维护中，暂时无法提供");

    public static final ExcepFactor E_FROZEN_USER = new ExcepFactor(
             HttpStatus.FORBIDDEN,
            1055,
            "forbid this operation.",
            "该账号因违规被冻结");

    public static final ExcepFactor E_BAN_USER = new ExcepFactor(
             HttpStatus.FORBIDDEN,
            1056,
            "forbid this operation.",
            "该账号已封禁");

    public static final ExcepFactor SOMETHING_ERROR = new ExcepFactor(
           HttpStatus.BAD_REQUEST,
            1057,
            "service is maintain",
            "服务器模块出错");

    public static final ExcepFactor E_EXCLUSIVE_PARAMS_ERROR = new ExcepFactor(
            HttpStatus.FORBIDDEN,
            1058,
            "exclusive params error.",
            "专属信息输入错误");


    public static final ExcepFactor ACCOUNT_EXISTS = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            2001,
            "account exists",
            "账号已存在");

    public static final ExcepFactor ACCOUNT_NOT_EXISTS = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            2002,
            "account not exists",
            "账号不存在");

    public static final ExcepFactor USERPASS_ERROR = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            2003,
            "username password error",
            "用户名或密码错误");

    public static final ExcepFactor PASSWORD_ERROR = new ExcepFactor(
            HttpStatus.BAD_REQUEST,
            2004,
            "password error",
            "密码错误");

    public static final ExcepFactor E_USER_AUTHFAIL = new ExcepFactor(
            HttpStatus.FORBIDDEN,
            2005,
            "authAccessible faild!",
            "认证失败");

    public static final ExcepFactor E_AUTH_PASSWORD_ERROR = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2006,
            "username or password error!",
            "用户名或密码不正确");

    public static final ExcepFactor E_AUTH_TOKEN_EXPIRES = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2007,
            "accessToken expires!",
            "Token 已过期");

    public static final ExcepFactor E_AUTH_REFRESH_TOKEN_INVALID = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2008,
            "invalid refresh accessToken",
            "Refresh Token 不合法");

    public static final ExcepFactor E_AUTH_INVALID_Client = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2009,
            "invalid client",
            "不合法的客户端");

    public static final ExcepFactor E_AUTH_INVALID_REDIRECT_URL = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2010,
            "invalid redirect url",
            "不合法的redirect url");

    public static final ExcepFactor E_AUTH_AUTHORIZE_CODE_ERROR = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2011,
            "generate authorize code error",
            "获取 Authorize Code 错误");

    public static final ExcepFactor E_AUTH_UNSUPPORTED_RESPONSE_TYPE = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2012,
            "unsupported response type",
            "不支持的response type");

    public static final ExcepFactor E_AUTH_UNSUPPORTED_GRANT_TYPE = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2013,
            "unsupported grant type",
            "grant type");

    public static final ExcepFactor E_AUTH_EMPTY_AUTHORIZE_CODE_ERROR = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2014,
            "authorize code must not be empty",
            "Authorize Code 不能为空");

    public static final ExcepFactor E_AUTH_EMPTY_USERNAME_PASSWORD_ERROR = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2015,
            "username or password must not be empty",
            "用户名和密码不能为空");

    public static final ExcepFactor E_AUTH_EMPTY_REFRESH_TOKEN_ERROR = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2016,
            "refresh accessToken must not be empty",
            "Refresh Token 不能为空");

    public static final ExcepFactor E_AUTH_ACCESS_TOKEN_ERROR = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2017,
            "generate access accessToken error",
            "获取Access Token错误");

    public static final ExcepFactor E_AUTH_SECRET_ERROR = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2018, "authAccessible secret error", "客户端 Secret 错误");

    public static final ExcepFactor E_AUTH_TOKEN_INVALID = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2019,
            "invalid accessToken",
            "Token 不合法");

    public static final ExcepFactor E_AUTH_NO_CLIENTID = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2020,
            "please add param client_id",
            "请上传client_id参数");

    public static final ExcepFactor E_AUTH_ILLEGAL_REQUEST = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2021,
            "illegal request",
            "不合法请求");

    public static final ExcepFactor E_AUTH_CLIENT_DISABLED = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2022,
            "client disabled",
            "client 被禁用");

    public static final ExcepFactor E_ILLEGAL_GUEST = new ExcepFactor(
            HttpStatus.UNAUTHORIZED,
            2023,
            "illegal guest",
            "不合法访客");

    public static final ExcepFactor E_USER_RATE_LIMIT = new ExcepFactor(
            HttpStatus.FORBIDDEN,
            2024,
            "User request limit",
            "用户请求限制");

    public static final ExcepFactor E_IP_RATE_LIMIT = new ExcepFactor(
            HttpStatus.FORBIDDEN,
            2025,
            "IP request limit",
            "IP请求限制");

    public static final ExcepFactor E_USER_IP_RATE_LIMIT = new ExcepFactor(
            HttpStatus.FORBIDDEN,
            2026,
            "User and IP request limit",
            "用户和IP请求限制");

    public static final ExcepFactor E_API_RATE_LIMIT = new ExcepFactor(
            HttpStatus.FORBIDDEN,
            2028,
            "Api request limit",
            "Api请求限制");

    public static final ExcepFactor E_USER_PWD_TRYLIMIT = new ExcepFactor(
            HttpStatus.FORBIDDEN,
            2029,
            "Username and pwd authAccessible out of rate limit!",
            "用户名密码认证超过请求限制!");



    public static void main(String[] args) {
        printException(new PrintWriter(System.out));
    }

    public static void printException(PrintWriter out) {
        List<ExcepFactor> excepList = new ArrayList<ExcepFactor>(excepFactors);
        Collections.sort(excepList, new Comparator<ExcepFactor>() {
            @Override
            public int compare(ExcepFactor o1, ExcepFactor o2) {
                return o1.errorCode - o2.errorCode;
            }

        });
        for (ExcepFactor e : excepList) {
            out.println(e.toString());
            out.flush();
        }
    }

    public void setErrorMsgArgs(Object... args) {
        this.errerMsgArgs = args;
    }

    /**
     * 获取错误信息
     * @return
     */
    public String getMessage() {
        String msgFormat = errorMsgCn;
        if (errerMsgArgs == null || errerMsgArgs.length == 0) {
            return msgFormat;
        }
        try {
            return String.format(msgFormat, errerMsgArgs);
        } catch (Exception e) {
            LOGGER.error("getErrorMsg", e);
            return msgFormat;
        }
    }

    @Override
    public String toString() {
        return "ExcepFactor{" +
                "httpStatus=" + httpStatus +
                ", errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", errorMsgCn='" + errorMsgCn + '\'' +
                '}';
    }
}
