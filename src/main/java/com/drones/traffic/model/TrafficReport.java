package com.drones.traffic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by jcarretero on 10/05/2018.
 */
@Entity
public class TrafficReport {

    @GeneratedValue
    @Id
    @JsonIgnore
    private Long id;
    private String droneId;
    private String time;
    private String speed;
    private String trafficCondition;

    public TrafficReport() {
    }

    public TrafficReport(String droneId, String time, String speed, String trafficCondition) {
        this.droneId = droneId;
        this.time = time;
        this.speed = speed;
        this.trafficCondition = trafficCondition;
    }

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getTrafficCondition() {
        return trafficCondition;
    }

    public void setTrafficCondition(String trafficCondition) {
        this.trafficCondition = trafficCondition;
    }

    @Override
    public String toString() {
        return "TrafficReport{" +
                "droneId='" + droneId + '\'' +
                ", time='" + time + '\'' +
                ", speed='" + speed + '\'' +
                ", trafficCondition='" + trafficCondition + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrafficReport that = (TrafficReport) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (droneId != null ? !droneId.equals(that.droneId) : that.droneId != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (speed != null ? !speed.equals(that.speed) : that.speed != null) return false;
        return trafficCondition != null ? trafficCondition.equals(that.trafficCondition) : that.trafficCondition == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (droneId != null ? droneId.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (speed != null ? speed.hashCode() : 0);
        result = 31 * result + (trafficCondition != null ? trafficCondition.hashCode() : 0);
        return result;
    }
}
