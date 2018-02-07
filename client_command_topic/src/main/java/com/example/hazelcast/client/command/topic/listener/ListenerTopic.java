package com.example.hazelcast.client.command.topic.listener;

import com.example.hazelcast.client.command.topic.service.VehicleRestCommandServiceClientTopic;
import com.example.hazelcast.shared.model.Vehicle;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by netof on 07/02/2018.
 */
@Component
public class ListenerTopic implements MessageListener<Vehicle> {
    @Autowired
    VehicleRestCommandServiceClientTopic vehicleRestCommandServiceClientTopic;

    @Override
    public void onMessage(Message<Vehicle> message) {
        try{
            String topicName = String.valueOf(message.getSource());
            Vehicle vehicleFromMessage = message.getMessageObject();

            if("vehiclesTopicSave".equals(topicName)){
                vehicleRestCommandServiceClientTopic.save(vehicleFromMessage);
            }
            if("vehiclesTopicUpdate".equals(topicName)){
                vehicleRestCommandServiceClientTopic.updateVehicle(vehicleFromMessage);
            }
            if("vehiclesTopicDelete".equals(topicName)){
                vehicleRestCommandServiceClientTopic.deleteVehicle(vehicleFromMessage.getVehicleId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
