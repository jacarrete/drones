package com.drones.traffic.helper;

import com.drones.traffic.model.TrafficReport;
import com.drones.traffic.service.TrafficReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.drones.traffic.helper.Constants.LINE_SEPARATOR;

/**
 * Created by jcarretero on 10/05/2018.
 */

public class FileWriter implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(FileWriter.class);
    
    private FileOutputStream out;
    private ConcurrentLinkedQueue<TrafficReport> queue;
    private TrafficReportService trafficReportService;

    public FileWriter() {
    }

    public FileWriter(FileOutputStream out, ConcurrentLinkedQueue<TrafficReport> queue, TrafficReportService trafficReportService) {
        this.out = out;
        this.queue = queue;
        this.trafficReportService = trafficReportService;
    }

    @Override
    public void run() {
        synchronized (queue) {
            while (true) {
                if (!queue.isEmpty()) {
                    try {
                        TrafficReport trafficReport = queue.poll();
                        out.write(trafficReport.toString().getBytes("UTF-8"));
                        out.write(LINE_SEPARATOR.getBytes("UTF-8"));
                        trafficReportService.saveTrafficReport(trafficReport);
                    } catch (IOException e) {
                        log.error("Error FileWriter: ", e);
                    }
                }
            }
        }

    }
}
