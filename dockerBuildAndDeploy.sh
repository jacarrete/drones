#!/usr/bin/env bash
echo 'Starting stack up ...'
docker stack deploy -c docker-compose.yml demo

echo 'Waiting for rabbitMQ to be started'
sleep 10

echo 'Running the application'
mvn spring-boot:run