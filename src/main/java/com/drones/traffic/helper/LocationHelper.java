package com.drones.traffic.helper;

import static com.drones.traffic.helper.Constants.MAX_DISTANCE_TO_STATION;

/**
 * Created by jcarretero on 10/05/2018.
 */
public class LocationHelper {

    public static boolean isLocationCloseToStation(double latitude, double longitude,
                                                   double stationLatitude, double stationLongitude) {
        return distanceInMeters(latitude, longitude, stationLatitude, stationLongitude) <= MAX_DISTANCE_TO_STATION;
    }

    private static double distanceInMeters(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371 * 1000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return earthRadius * c;
    }
}
