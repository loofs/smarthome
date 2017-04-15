package com.yewei.app.server.framework.conf;

import com.yewei.app.server.framework.type.HttpHeader;
import com.yewei.app.server.pojo.dto.ResponseParam;
import com.yewei.app.server.util.GlobalConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by lenovo on 2017/4/9.
 * Swagger配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket restApi() {
        List<Parameter> parametersList = new ArrayList<Parameter>();
        parametersList.add(new ParameterBuilder().name(HttpHeader.ACCESS_TOKEN)
                .description("接口访问令牌，登录成功后会获得此令牌，后续HTTP请求Header中需携带此参数用于身份认证")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .groupName("test")
//                .genericModelSubstitutes(DeferredResult.class)
                .genericModelSubstitutes(ResponseEntity.class)
//                .genericModelSubstitutes(ResponseParam.class)
                .useDefaultResponseMessages(false)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(regex("/api/.*"))//过滤的接口
                .apis(RequestHandlerSelectors.basePackage(GlobalConstants.API_CONTROLLER_PACKAGE))
                .build().globalOperationParameters(parametersList);
    }

    private ApiInfo apiInfo () {
        return new ApiInfoBuilder()
                .title("EH Platform API")//大标题
                .description("EH Platform's REST API, all the applications could access the Object model data via JSON.")//详细描述
                .version("1.0")//版本
                .termsOfServiceUrl("NO terms of service")
                .contact(new Contact("XXX ", "http://www.example.com/", "example@mail.com"))
                .build();
    }
}
