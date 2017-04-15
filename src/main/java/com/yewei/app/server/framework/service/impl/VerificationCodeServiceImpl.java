package com.yewei.app.server.framework.service.impl;

import com.yewei.app.server.framework.exception.EngineException;
import com.yewei.app.server.framework.exception.ExcepFactor;
import com.yewei.app.server.framework.service.CacheService;
import com.yewei.app.server.framework.service.VerificationCodeService;
import com.yewei.app.server.framework.type.VerificationAction;
import com.yewei.app.server.util.StreamUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * Created by lenovo on 2017/4/8.
 * 验证码服务实现类
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationCodeServiceImpl.class);

    // 验证码缓存前缀
    private static final String VERIFICATION_CODE_CACHE_HEADER = "verfication.code.";

    // 短信发送成功标记
    private static final String SMS_SUCCESS_FLAG = "<message>ok</message>";

    @Value("${app.sms.url}")
    private String smsServiceUrl;

    @Value("${app.sms.userid}")
    private String userid;

    @Value("${app.sms.account}")
    private String account;

    @Value("${app.sms.password}")
    private String password;

    @Value("${app.sms.content:【SmartHome】您本次验证码：%s，退订回T}")
    private String content;

    //  短信发送间隔，默认60秒
    @Value("${app.sms.interval:60}")
    private int smsSendInterval;

    //  短信验证码过期时间，默认15分钟
    @Value("${app.sms.expires:15}")
    private long verificationCodeExpires;

    // HTTP客户端，用于调用短信接口
    private CloseableHttpClient httpClient = HttpClients.createDefault();

    // 随机数生成器，用于生成验证码
    private Random random = new Random();

    // 缓存服务
    @Autowired
    private CacheService cacheService;

    @Override
    public void sendVerifactionCode(String phoneNumber, VerificationAction action) {
        LOGGER.info(action + "发送验证码到手机：" + phoneNumber);
        String randomCode = "" + (random.nextInt(9000) + 1000);
        String sendContent = String.format(content, randomCode);

        if (StringUtils.isNoneEmpty(cacheService.getCacheData(getCacheKey(phoneNumber, action)))) {
            throw new EngineException(ExcepFactor.E_SMS_OUTOFLIMIT);
        }

        LOGGER.info("发送短信内容到" + phoneNumber + ": " + sendContent);
        try {
            URI uri = new URIBuilder(smsServiceUrl)
                    .setParameter("userid", userid)
                    .setParameter("account", account)
                    .setParameter("password", password)
                    .setParameter("mobile", phoneNumber)
                    .setParameter("content", sendContent)
                    .setParameter("sendTime", "")
                    .setParameter("action", "send")
                    .setParameter("extno", "")
                    .build();
            LOGGER.info("URL:" + uri.toString());
            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String sendResult = EntityUtils.toString(entity);
                    LOGGER.info("短信发送结果：" + sendResult);
                    if (!sendResult.contains(SMS_SUCCESS_FLAG)) {
                        LOGGER.warn("短信发送失败，发送结果中未包含成功标志：" + SMS_SUCCESS_FLAG);
                        throw new EngineException(ExcepFactor.SOMETHING_ERROR);
                    }
                }
            } finally {
                StreamUtils.closeStream(response);
            }
        } catch (URISyntaxException | IOException e) {
            LOGGER.error("发送短信验证码出错", e);
            throw new EngineException(ExcepFactor.SOMETHING_ERROR);
        }

        // 缓存用户验证码，用于后续验证
        cacheService.cacheData(getCacheKey(phoneNumber, action), randomCode, System.currentTimeMillis() + verificationCodeExpires * 60 * 1000);

    }

    @Override
    public boolean validateCode(String phoneNumber, VerificationAction action, String code) {
        LOGGER.info("验证手机验证码，手机号：" + phoneNumber + "，验证码：" + code);
        return StringUtils.equals(cacheService.getCacheData(getCacheKey(phoneNumber, action)), code);
    }

    /**
     * 获取缓存键值
     * @param phoneNumber
     * @param action
     * @return
     */
    private String getCacheKey(String phoneNumber, VerificationAction action) {
        return VERIFICATION_CODE_CACHE_HEADER + phoneNumber + "." + action;
    }
}
