package com.example.service;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.EnhancedApnsNotification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: dj
 * @create: 2021-01-09 09:22
 * @description:
 */
@Service
public class IosService {

    @Value("${ios.ttl}")
    private int ttl;

    @Value("${ios.password}")
    private String password;

    private static ApnsService apnsService;

    /**
     * 初始化sdk
     */
    private void initSdk() {
        apnsService =
                APNS.newService()
                        .withCert("certificate.p12", password)
                        .withSandboxDestination()
                        .build();
    }

    public void pushData(String token, String title, String body, String clickAction, String channelId) {
//        String payload = APNS.newPayload().alertBody("Can't be simpler than this!").build();

        String payload = APNS.newPayload()
                .badge(3)
                .customField("secret", "what do you think?")
                .localizedKey("GAME_PLAY_REQUEST_FORMAT")
                .localizedArguments("Jenna", "Frank")
                .actionKey("Play").build();

        int now = (int) (System.currentTimeMillis() / 1000);

        EnhancedApnsNotification notification = new EnhancedApnsNotification(EnhancedApnsNotification.INCREMENT_ID(),
                now + ttl,
                token,
                payload);

        apnsService.push(notification);

    }

}
