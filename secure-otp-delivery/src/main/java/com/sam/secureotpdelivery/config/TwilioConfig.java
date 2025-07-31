package com.sam.secureotpdelivery.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.naming.ConfigurationException;

@Configuration
@ConfigurationProperties(prefix = "twilio")
@Data
@Slf4j
public class TwilioConfig {

    private String accountSid;
    private String authToken;
    private String fromNumber;

    @PostConstruct
    public void init() throws ConfigurationException {
        if (!StringUtils.hasText(accountSid) || !StringUtils.hasText(authToken) || !StringUtils.hasText(fromNumber)) {
            throw new ConfigurationException("Twilio configuration is incomplete...");
        }

        Twilio.init(accountSid, authToken);
        log.info("Twilio initialized successfully. SID suffix: {}", accountSid.substring(accountSid.length() - 4));
    }
}
