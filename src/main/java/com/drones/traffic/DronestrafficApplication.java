package com.drones.traffic;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class DronestrafficApplication {

	public static void main(String[] args) {
		SpringApplication.run(DronestrafficApplication.class, args);
	}
}
