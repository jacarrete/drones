package com.drones.traffic.service;

import com.drones.traffic.dao.TrafficReportRepository;
import com.drones.traffic.model.TrafficReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jcarretero on 10/05/2018.
 */
@Service("trafficReportService")
public class TrafficReportService {

    @Autowired
    private TrafficReportRepository trafficReportRepository;

    @Transactional
    public List<TrafficReport> getTrafficReport() {
        return trafficReportRepository.findAll();
    }

    @Transactional
    public TrafficReport saveTrafficReport(TrafficReport trafficReport) {
        return trafficReportRepository.save(trafficReport);
    }

}
