package com.yewei.app.server.framework.conf;

import com.yewei.app.server.framework.AuthInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2017/3/27.
 */
@Service
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);

    @Resource
    private AuthInterceptor authInterceptor;

    /**
     * 拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册监控拦截器
        registry.addInterceptor(authInterceptor).addPathPatterns("/api/**");
    }

}
