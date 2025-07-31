package com.sam.secureotpdelivery.service;

import com.sam.secureotpdelivery.common.exception.SmsSendFailureException;
import com.sam.secureotpdelivery.common.util.SecureOtpDeliveryUtils;
import com.sam.secureotpdelivery.config.TwilioConfig;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsSenderService {

    private final TwilioConfig twilioConfig;

    @Async
    public void sendOtpSms(String toPhoneNumber, String otp) {
        String messageBody = "Your OTP is: " + otp;
        String maskedPhone = SecureOtpDeliveryUtils.maskMobile(toPhoneNumber);

        try {
            Message.creator(
                    new PhoneNumber("+91" + toPhoneNumber),                 // Ensure this is pre-validated
                    new PhoneNumber(twilioConfig.getFromNumber()),
                    messageBody
            ).create();

            log.info("OTP SMS successfully sent to {}", maskedPhone);

        } catch (ApiException ex) {
            log.error("Twilio API failure while sending SMS to {}: [{}] {}", maskedPhone, ex.getCode(), ex.getMessage());
            throw new SmsSendFailureException("Twilio SMS send failed: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("Unexpected failure while sending SMS to {}: {}", maskedPhone, ex.getMessage(), ex);
            throw new SmsSendFailureException("Unexpected SMS send failure: " + ex.getMessage(), ex);
        }

    }
}
