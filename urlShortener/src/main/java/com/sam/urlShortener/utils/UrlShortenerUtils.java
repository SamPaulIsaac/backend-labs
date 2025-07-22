package com.sam.urlShortener.utils;


import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UrlShortenerUtils {
    public static final String APP_USER = "APP USER";
    public static final LocalDateTime CURRENT_LOCAL_DATE_TIME = LocalDateTime.now();
    public static final LocalDateTime EXPIRATION_TIME = CURRENT_LOCAL_DATE_TIME.plusMinutes(1);
    public static final int VALUE = 1_000_000;
    public static final String BASE64ENCODE = "abcefghijklmnopqrstuvwxyzABCEFGHIJKLMNOPQRSTUVWXYZ0123456789";

}
