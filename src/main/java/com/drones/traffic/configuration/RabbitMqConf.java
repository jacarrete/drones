package com.drones.traffic.configuration;

import com.drones.traffic.helper.FileWriter;
import com.drones.traffic.model.TrafficReport;
import com.drones.traffic.service.TrafficReportService;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.drones.traffic.helper.Constants.TRAFFIC_REPORT_FILE;

/**
 * Created by jcarretero on 10/05/2018.
 */

@Configuration
public class RabbitMqConf implements RabbitListenerConfigurer {

    @Bean
    public ConcurrentLinkedQueue<TrafficReport> generateQueue() {
        return new ConcurrentLinkedQueue<>();
    }

    @Bean
    public int getFileWriter(TrafficReportService trafficReportService) {
        FileOutputStream out= null;
        try {
            out = new FileOutputStream(new File(TRAFFIC_REPORT_FILE), true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        new Thread(new FileWriter(out, generateQueue(), trafficReportService)).start();
        return 1;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar reg) {
        reg.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}
