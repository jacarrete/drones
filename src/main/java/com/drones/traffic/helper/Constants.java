package com.drones.traffic.helper;

/**
 * Created by jcarretero on 10/05/2018.
 */
public class Constants {

    public static final Double DRONE_SPEED = 24.1;

    public static final int SIMULATION_TIMEOUT = 81000;

    public static final String TOPIC_EXCHANGE_NAME = "topic.drone";

    public static final String ROUTING_DRONE_1 = "5937";
    public static final String ROUTING_DRONE_2 = "6043";

    public static final int QUEUE_SIZE = 10;

    public static final int MAX_DISTANCE_TO_STATION = 350; //Meters

    public static final String DRONE1_ROUTE_FILE = "5937.csv";
    public static final String DRONE2_ROUTE_FILE = "6043.csv";
    public static final String TUBE_STATIONS_FILE = "tube.csv";
    public static final String TRAFFIC_REPORT_FILE = "trafficReport.txt";
    public static final String SEPARATOR = ",";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
}
