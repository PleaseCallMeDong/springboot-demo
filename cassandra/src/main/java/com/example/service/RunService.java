package com.example.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import com.example.dao.MonitorDataDAO;
import com.example.domain.BaseTrackBO;
import com.example.domain.MonitorDataDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: dj
 * @create: 2020-12-25 17:04
 * @description:
 */
@Slf4j
@Service
public class RunService {

    @Resource
    private MonitorDataDAO monitorDataDAO;

    @PostConstruct
    public void run() {

        List<MonitorDataDO> list = new ArrayList<>();

        BaseTrackBO baseTrackBO = new BaseTrackBO();

        for (int i = 0; i < 10000; i++) {
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
            list.add(monitorDataDO);
        }


        Long start = System.currentTimeMillis();

        monitorDataDAO.insert(list);

        Long end = System.currentTimeMillis();
        end = end - start;
        log.error("耗时:{}", end);

    }

}
