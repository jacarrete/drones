package com.drones.traffic.components;

import com.google.gson.Gson;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.drones.traffic.dto.DroneMessage;
import com.drones.traffic.model.TrafficReport;
import com.drones.traffic.service.DroneMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeoutException;

import static com.drones.traffic.helper.Constants.*;

/**
 * Created by jcarretero on 10/05/2018.
 */

@Service
public class Dispatcher implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Dispatcher.class);

    private final DroneMessageService droneMessageService;
    private Gson gson = new Gson();
    private final  ConcurrentLinkedQueue<TrafficReport> queue;

    @Autowired
    public Dispatcher(DroneMessageService droneMessageService, ConcurrentLinkedQueue<TrafficReport> queue) {
        this.droneMessageService = droneMessageService;
        this.queue = queue;
    }

    @Override
    public void run(String... args) throws Exception {
        sendMessages();
        startDrones();
    }

    private void sendMessages() {
        log.info("Sending messages...");
        Map<String, List<DroneMessage>> droneMessagesMap = droneMessageService.getDroneMessages();
        Runnable task1 = () -> publishMessages(droneMessagesMap.get(ROUTING_DRONE_1), ROUTING_DRONE_1);
        Runnable task2 = () -> publishMessages(droneMessagesMap.get(ROUTING_DRONE_2), ROUTING_DRONE_2);
        new Thread(task1).start();
        new Thread(task2).start();
    }

    private Channel createChannelConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(QUEUE_SIZE);
        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        return channel;
    }

    private void publishMessages(List<DroneMessage> droneMessageList, String droneId) {
        Connection connection = null;
        Channel channel;
        try {
            channel = createChannelConnection();
            for (DroneMessage droneMessage : droneMessageList) {
                log.info("Sending to droneId: " + droneId + " - " + droneMessage.toString());
                channel.basicPublish(TOPIC_EXCHANGE_NAME, droneId, null, gson.toJson(droneMessage).getBytes());
            }
        } catch (TimeoutException | IOException e) {
            log.error("Exception creating channel connection: ", e);
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    log.error("Exception closing Connection: ", e);
                }
            }
        }
    }

    private void startDrones() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            new DroneReceiver(connection.createChannel(), ROUTING_DRONE_1, queue).startDrone();
            new DroneReceiver(connection.createChannel(), ROUTING_DRONE_2, queue).startDrone();
        } catch (IOException | TimeoutException e) {
            log.error("Start drone exception: ", e);
        }
    }

}
