package com.drones.traffic.helper;

/**
 * Created by jcarretero on 10/05/2018.
 */
public enum TrafficConditionType {
    HEAVY, LIGHT, MODERATE;

    public static TrafficConditionType getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
