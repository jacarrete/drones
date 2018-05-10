package com.drones.traffic.service;

import com.drones.traffic.dto.DroneMessage;
import com.drones.traffic.helper.FileReader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.drones.traffic.helper.Constants.*;
import static com.drones.traffic.helper.LocationHelper.isLocationCloseToStation;

/**
 * Created by jcarretero on 10/05/2018.
 */
@Service("droneMessageService")
public class DroneMessageService {

    public Map<String, List<DroneMessage>> getDroneMessages() {
        final Map<String, List<DroneMessage>> droneMessages = new HashMap<>();

        Consumer<String> GENERATE_LOCATIONS = line -> {
            String[] p = line.replace("\"", "").split(",");
            String droneId = p[0];
            droneMessages.computeIfAbsent(droneId, k -> new ArrayList<>());
            String[] time = p[3].split(" ");
            int time2int = Integer.parseInt(time[1].replace(":", ""));
            if (time2int <= SIMULATION_TIMEOUT) {
                droneMessages.get(droneId).add(new DroneMessage(Double.valueOf(p[1]), Double.valueOf(p[2]), p[3]));
            }
        };

        Consumer<String> GENERATE_TRAFFIC_CHECKS = line -> {
            String[] p = line.split(SEPARATOR);
            double latitude = Double.valueOf(p[1]);
            double longitude = Double.valueOf(p[2]);
            for (List<DroneMessage> droneMessageList : droneMessages.values()) {
                droneMessageList.forEach(droneMessage -> {
                    if (!droneMessage.isCheckTraffic() && isLocationCloseToStation(droneMessage.getLatitude(),
                            droneMessage.getLongitude(), latitude, longitude)) {
                        droneMessage.setCheckTraffic(true);
                    }
                });
            }
        };

        FileReader.readFile(DRONE1_ROUTE_FILE, GENERATE_LOCATIONS);
        FileReader.readFile(DRONE2_ROUTE_FILE, GENERATE_LOCATIONS);
        FileReader.readFile(TUBE_STATIONS_FILE, GENERATE_TRAFFIC_CHECKS);
        return droneMessages;
    }
}
