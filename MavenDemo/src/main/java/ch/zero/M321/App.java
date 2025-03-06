package ch.zero.M321;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        String topic = "Demo";
        if(args.length > 0) {
            topic = args[0];
            try {

                MqttClient client = new MqttClient("tcp://localhost:1883", UUID.randomUUID().toString());
                client.connect();
                client.subscribe("KILL", new IMqttMessageListener() {
    
                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        System.out.println("received Topic: " + topic + ", Message: " + message);
                    }
                });
                double inkrement = 0;
    
                while (true) {
                    Double value = Math.sin(inkrement);
                    MqttMessage message = new MqttMessage(String.valueOf(value).getBytes());
                    inkrement= (inkrement+0.1) % (2*Math.PI);
                    client.publish(topic, message);
                    Thread.sleep(1000);
                }

    
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        
    }
}
