package com.example.domain;

import cn.hutool.core.date.DateUtil;
import com.example.pattern.MySysPattern;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: dj
 * @create: 2020-12-18 15:23
 * @description: 基础轨迹数据
 * 所有的k2数据都转成该类
 * 然后再进行处理过滤
 */
@Data
public class BaseTrackBO implements Serializable {

    /**
     * 数据源平台唯一标识码
     */
    private String deviceSysId;

    /**
     * 采集时间/设备时间
     */
    private String deviceTime;

    /**
     * 数据（平台）产生时间
     */
    private String serviceTime;

    /**
     * 路由设备类型
     */
    private String routeType;

    /**
     * 路由设备编号
     */
    private String routeId;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    private String deviceId;

    /**
     * 错误类型
     * 0    正确
     * 1    时间错误
     * 2    经纬度错误
     * 3    设备id错误
     */
    private Integer errorType;


    /**
     * 对轨迹进行纠偏
     *
     * @return BaseTrackBO
     */
    public BaseTrackBO correctTrack() {

        String routeType = this.getRouteType();
        if (routeType.equalsIgnoreCase(MySysPattern.NULL_STR)) {
            routeType = MySysPattern.ZERO_STR;
        }
        this.routeType = routeType;

        String routeId = this.getRouteId();
        if (routeId.equalsIgnoreCase(MySysPattern.NULL_STR)) {
            routeId = MySysPattern.ZERO_STR;
        }
        this.routeId = routeId;

        String latitude = this.getLatitude();
        String longitude = this.getLongitude();

        if (latitude.equalsIgnoreCase(MySysPattern.NULL_STR)) {
            latitude = MySysPattern.ZERO_STR;
        }

        if (longitude.equalsIgnoreCase(MySysPattern.NULL_STR)) {
            longitude = MySysPattern.ZERO_STR;
        }
        this.latitude = latitude;
        this.longitude = longitude;

        return this;
    }

    public Boolean haveError() {
        if (this.errorType != null) {
            return errorType != 0;
        }
        //1.经纬度是否为0或为null
        if (this.routeType.equalsIgnoreCase(MySysPattern.NULL_STR) ||
                this.routeId.equalsIgnoreCase(MySysPattern.NULL_STR)
                || this.latitude.equalsIgnoreCase(MySysPattern.NULL_STR)
                || this.longitude.equalsIgnoreCase(MySysPattern.NULL_STR)
                || this.latitude.equalsIgnoreCase(MySysPattern.ZERO_STR)
                || this.longitude.equalsIgnoreCase(MySysPattern.ZERO_STR)) {
            this.setErrorType(2);
            return true;
        }

        //2.是否飘出中国范围
        //中国 经度范围：73°33′E至135°05′E
        //纬度范围：3°51′N至53°33′N

        double latitudeDouble = Double.parseDouble(latitude);

        double longitudeDouble = Double.parseDouble(longitude);

        if (MySysPattern.CN_LATITUDE_MAX < latitudeDouble || MySysPattern.CN_LATITUDE_MIN > latitudeDouble) {
            this.setErrorType(2);
            return true;
        }

        if (MySysPattern.CN_LONGITUDE_MAX < longitudeDouble || MySysPattern.CN_LONGITUDE_MIN > longitudeDouble) {
            this.setErrorType(2);
            return true;
        }

        //大于1个小时
        long big = 60 * 60 * 1000;
        long now = System.currentTimeMillis();
        long deviceTimeL = DateUtil.parseDateTime(this.deviceTime).getTime();
        long time = deviceTimeL - now;
        if (big < time) {
            this.setErrorType(1);
            return true;
        }
        this.setErrorType(0);
        return false;
    }

}
