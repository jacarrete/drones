package com.drones.traffic.controller;

import com.drones.traffic.model.TrafficReport;
import com.drones.traffic.service.TrafficReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jcarretero on 10/05/2018.
 */
@RestController
public class TrafficReportController {

    @Autowired
    private TrafficReportService trafficReportService;

    @GetMapping("/trafficReport")
    public List<TrafficReport> getTrafficReport() {
        return trafficReportService.getTrafficReport();
    }
}
