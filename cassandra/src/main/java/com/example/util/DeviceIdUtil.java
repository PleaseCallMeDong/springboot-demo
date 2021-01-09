package com.example.util;

/**
 * @author : cjl
 * @description :
 * @date : create in 2020-9-2 13:29
 */
public class DeviceIdUtil {

    /**
     * 返回设备类型 10进制字符类型
     *
     * @param devSysId
     * @return
     */
    public static Long getDevType(String devSysId) {
        long devType = Long.parseLong(devSysId) >> 32;
        return Long.valueOf(devType);
    }

    public static Long getDevId(String devSysId) {
        long devId = Long.parseLong(devSysId) & 0xFFFFFFFFL;
//        Long.toHexString(devId).toUpperCase();
        return devId;
    }

}
