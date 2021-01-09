package com.example.dao;

import com.example.domain.MonitorDataDO;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: dj
 * @create: 2020-12-25 17:05
 * @description:
 */
@Repository
public interface MonitorDataDAO extends CassandraRepository<MonitorDataDO, Long> {
}
