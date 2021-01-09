package com.example.service;

import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

/**
 * @author: dj
 * @create: 2021-01-09 09:12
 * @description:
 */
@Slf4j
@Service
public class FcmService {

    @Value("${fcm.ttl}")
    private int ttl;

    @Value("${fcm.packageName}")
    private String packageName;

    //获取AndroidConfig.Builder对象
//    private static AndroidConfig.Builder androidConfigBuilder = AndroidConfig.builder();

    //获取AndroidNotification.Builder对象
//    private static AndroidNotification.Builder androidNotifiBuilder = AndroidNotification.builder();

    public static FirebaseApp firebaseApp;

    private static final String FIREBASE_DB_URL = "https://你的firebase地址.firebaseio.com";

//    private static final String FIREBASE_STORAGE_BUCKET = "你的firebase地址";

//    private static final String FIREBASE_PROJECT_ID = "你的firebase地址";

    /**
     * 初始化sdk
     */
    private void initSdk() {
        try {
            FileInputStream refreshToken = new FileInputStream("firebase-adminsdk.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl(FIREBASE_DB_URL)
                    .build();

            firebaseApp = FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            log.warn("fcm的sdk初始化错误", e);
        }
    }

    /**
     * 单设备推送
     *
     * @param token       用户设备唯一标识列表
     * @param title       推送题目
     * @param body        推送内容
     * @param clickAction 这个是点击消息后的触发事件 ，如果想要触发app可以默认设置为OPEN_STOCK_ACTIVITY
     * @param channelId   设置发送的频道的id
     */
    public void pushData(String token, String title, String body, String clickAction, String channelId) {
        //获取实例
        FirebaseApp firebaseApp = FirebaseApp.getInstance();
        //实例为空的情况
        if (firebaseApp == null) {
            return;
        }

        try {
            //构建消息内容
            //获取AndroidConfig.Builder对象
            AndroidConfig.Builder androidConfigBuilder = AndroidConfig.builder();
            //获取AndroidNotification.Builder对象
            AndroidNotification.Builder androidNotifiBuilder = AndroidNotification.builder();

            //可以存放一个数据信息进行发送，使得app开发客户端可以接受信息
//            androidConfigBuilder.putData("test", "this is a test data");
            //设置包名
            androidConfigBuilder.setRestrictedPackageName(packageName);
            //设置过期时间 官方文档以毫秒为单位
            androidConfigBuilder.setTtl(ttl);
            // 设置消息标题
            androidNotifiBuilder.setTitle(title);
            // 设置消息内容
            androidNotifiBuilder.setBody(body);
            //设置触发事件
            androidNotifiBuilder.setClickAction(clickAction);
            androidNotifiBuilder.setChannelId(channelId);
            AndroidNotification androidNotification = androidNotifiBuilder.build();
            androidConfigBuilder.setNotification(androidNotification);
            AndroidConfig androidConfig = androidConfigBuilder.build();

            //在进行消息发送之前要设置代理  这个非常重要，因为访问谷歌的服务器需要通过代理服务器在进行访问
//            initProxy("yourHost",80,"yourUsername","yourPassword");
//向所有的设备推送消息
            //构建消息
            MulticastMessage message = MulticastMessage.builder()
                    .addToken(token)
                    .setAndroidConfig(androidConfig)
                    .build();
            BatchResponse batchResponse = FirebaseMessaging.getInstance(firebaseApp).sendMulticast(message);
            log.info("单个设备推送成功:{}", batchResponse.getResponses());
        } catch (Exception e) {
            log.warn("fcm推送失败", e);
        }

    }

}
