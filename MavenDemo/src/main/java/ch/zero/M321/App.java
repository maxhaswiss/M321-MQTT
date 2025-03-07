package ch.zero.M321;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        String pubTopic;
        String subTopic;
        if(args.length > 1) {
            pubTopic = args[0];
            subTopic = args[1];
            try {

                MqttClient client = new MqttClient("tcp://localhost:1883", UUID.randomUUID().toString());
                client.connect();
                client.subscribe(subTopic, new IMqttMessageListener() {
    
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
                    client.publish(pubTopic, message);
                    Thread.sleep(1000);
                }

    
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("please provide a pubTopic and a subTopic");
        }

        
    }
}
