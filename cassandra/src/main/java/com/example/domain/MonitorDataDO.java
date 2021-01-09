package com.example.domain;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.example.util.DeviceIdUtil;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @author cjl
 * @date 2020/12/18 14:08
 * @description
 **/
@Table("monitordata")
@Data
public class MonitorDataDO implements Serializable {

    /**
     * 设备唯一标识
     */
    @PrimaryKey
    private Long tno;

    /**
     * 设备时间
     */
    @Column
    private Date ttime;

    /**
     * 设备类型
     */
    @Column
    private Long ttype;

    /**
     * 平台时间
     */
    @Column
    private Date rtime;

    /**
     * 纬度
     */
    @Column
    private Float tlat;

    /**
     * 经度
     */
    @Column
    private Float tlng;

    /**
     * 路由设备类型
     */
    @Column
    private Integer rtype;

    /**
     * 路由设备id
     */
    @Column
    private Long rno;

    /**
     * 入库时间
     */
    @Column
    private Date dtime;

    @Column
    private Integer azimuth;
    @Column
    private Integer dsource;
    @Column
    private Integer hdop;
    @Column
    private Integer mph;
    @Column
    private Integer satellite;
    @Column
    private Integer tpower;
    @Column
    private Integer tstate;

    public MonitorDataDO(String tno, Date ttime, Date rtime, String ttype, String tlat, String tlng, String rtype,
                         String rno) {
        this.tno = Long.parseLong(tno);
        this.ttime = ttime;
        this.rtime = rtime;
        this.ttype = Long.parseLong(ttype);
        this.tlat = Float.parseFloat(tlat);
        this.tlng = Float.parseFloat(tlng);
        this.rtype = Integer.parseInt(rtype);
        this.rno = Long.parseLong(rno);
        this.dtime = new Date();
    }

    public MonitorDataDO(BaseTrackBO baseTrackBO) {
        this.tno = Long.parseLong(baseTrackBO.getDeviceSysId());
        this.ttime = DateUtil.parseDateTime(baseTrackBO.getDeviceTime());
        this.ttype = DeviceIdUtil.getDevType(baseTrackBO.getDeviceSysId());
        this.rtime = DateUtil.parseDateTime(baseTrackBO.getServiceTime());
        this.rtype = Integer.parseInt(baseTrackBO.getRouteType());
        this.rno = Long.parseLong(baseTrackBO.getRouteId());
        this.tlat = Float.parseFloat(baseTrackBO.getLatitude());
        this.tlng = Float.parseFloat(baseTrackBO.getLongitude());
        this.dtime = DateUtil.date();
        this.azimuth = 0;
        // 1表示自身轨迹 2 表示路由 99表示其他
        long routeSysId = (long) this.rtype << 32 | this.rno;
        if (this.tno == routeSysId) {
            this.dsource = 1;
        } else {
            this.dsource = 2;
        }
        this.hdop = 0;
        this.mph = 0;
        this.satellite = 0;
        this.tpower = 0;
        this.tstate = 0;
    }

    public static void main(String[] args) {
        BaseTrackBO baseTrackBO = new BaseTrackBO();

        baseTrackBO.setDeviceId(RandomUtil.randomLong(1, 99999) + "");
        Date date = new Date();
        DateTime dateTime = RandomUtil.randomDate(date, DateField.SECOND, -24 * 3600 * 15, 24 * 3600 * 15);
        baseTrackBO.setDeviceTime(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        baseTrackBO.setServiceTime(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        baseTrackBO.setRouteType("1040");
        baseTrackBO.setRouteId(RandomUtil.randomLong(1, 99999) + "");
        baseTrackBO.setLatitude(RandomUtil.randomDouble(32, 33, 6, RoundingMode.FLOOR) + "");
        baseTrackBO.setLongitude(RandomUtil.randomDouble(120, 121, 6, RoundingMode.FLOOR) + "");
        baseTrackBO.setDeviceSysId((32769L << 32 | Long.parseLong(baseTrackBO.getDeviceId())) + "");

        MonitorDataDO monitorDataDO = new MonitorDataDO(baseTrackBO);
        System.out.println(JSON.toJSONString(monitorDataDO));
    }
}
