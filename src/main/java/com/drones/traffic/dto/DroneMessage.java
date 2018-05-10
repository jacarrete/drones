package com.drones.traffic.dto;

import java.io.Serializable;

/**
 * Created by jcarretero on 10/05/2018.
 */
public class DroneMessage implements Serializable {

    private Double latitude;
    private Double longitude;
    private String time;
    private boolean checkTraffic = false;

    public DroneMessage () {}

    public DroneMessage(Double latitude, Double longitude, String time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isCheckTraffic() {
        return checkTraffic;
    }

    public void setCheckTraffic(boolean checkTraffic) {
        this.checkTraffic = checkTraffic;
    }

    @Override
    public String toString() {
        return "DroneMessage{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", time='" + time + '\'' +
                ", checkTraffic=" + checkTraffic +
                '}';
    }
}
