#!/bin/bash

# check if the user started the script with a parameter
if [ -z "$1" ]; then
  echo "Usage: $0 sensor_name"
  exit 1
fi

SENSOR_NAME=$1
TOPIC="sensors/$SENSOR_NAME"

echo "start the sensor with name: $SENSOR_NAME, send the dates to $TOPIC..."

# Endless loop for sending random values
while true; do
  VALUE=$(( RANDOM % 100 )) 
  mosquitto_pub -h localhost -t "$TOPIC" -m "$VALUE"
  echo "[$(date +%H:%M:%S)] $SENSOR_NAME sendet: $VALUE"
  sleep 1
done