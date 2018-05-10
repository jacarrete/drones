package com.drones.traffic.dao;

import com.drones.traffic.model.TrafficReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jcarretero on 10/05/2018.
 */
@Repository
public interface TrafficReportRepository extends JpaRepository<TrafficReport, Long> {
}
