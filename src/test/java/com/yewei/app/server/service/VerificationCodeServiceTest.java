package com.yewei.app.server.service;

import com.yewei.app.server.framework.service.VerificationCodeService;
import com.yewei.app.server.framework.type.VerificationAction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by lenovo on 2017/4/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VerificationCodeServiceTest {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Test
    public void sendVerifactionCode() {
        verificationCodeService.sendVerifactionCode("15921697315", VerificationAction.RESET_PASSWORD);
    }

    @Test
    public void validateCode() {
        verificationCodeService.validateCode("18059006496", VerificationAction.RESET_PASSWORD, "0000");
    }
}
