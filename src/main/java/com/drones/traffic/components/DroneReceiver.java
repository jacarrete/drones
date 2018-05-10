package com.drones.traffic.components;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.drones.traffic.dto.DroneMessage;
import com.drones.traffic.model.TrafficReport;
import com.drones.traffic.helper.TrafficConditionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.drones.traffic.helper.Constants.DRONE_SPEED;
import static com.drones.traffic.helper.Constants.QUEUE_SIZE;
import static com.drones.traffic.helper.Constants.TOPIC_EXCHANGE_NAME;

/**
 * Created by jcarretero on 10/05/2018.
 */

public class DroneReceiver {
    private static final Logger log = LoggerFactory.getLogger(DroneReceiver.class);
    private Channel channel;
    private String droneId;
    private ConcurrentLinkedQueue<TrafficReport> queue;
    private Gson gson = new Gson();

    public DroneReceiver(Channel channel, String droneId, ConcurrentLinkedQueue<TrafficReport> queue) {
        this.channel = channel;
        this.droneId = droneId;
        this.queue = queue;
    }

    public void startDrone() {
        String queueName = "";
        try {
            channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            channel.basicQos(QUEUE_SIZE);
            queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, TOPIC_EXCHANGE_NAME, droneId);
        } catch (IOException e) {
            log.error("Exception starting drone: ", e);
        }
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                DroneMessage droneMessage = gson.fromJson(new String(body, "UTF-8"), DroneMessage.class);
                log.info("Moving the Drone {} to: ({}, {})", droneId, droneMessage.getLatitude(), droneMessage.getLongitude());
                if (droneMessage.isCheckTraffic()) {
                    queue.add(generateTrafficReport(droneMessage, droneId));
                }
            }
        };
        try {
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TrafficReport generateTrafficReport(DroneMessage droneMessage, String droneId) {
        return new TrafficReport(droneId, droneMessage.getTime(), DRONE_SPEED.toString(), TrafficConditionType.getRandom().toString());
    }
}