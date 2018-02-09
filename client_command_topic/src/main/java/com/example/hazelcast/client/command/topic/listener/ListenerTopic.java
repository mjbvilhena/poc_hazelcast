package com.example.hazelcast.client.command.topic.listener;

import com.example.hazelcast.client.command.topic.service.VehicleRestCommandServiceClientTopic;
import com.example.hazelcast.shared.interface_message.IVehicleMessage;
import com.example.hazelcast.shared.message.Impl.VehicleDeleteMessage;
import com.example.hazelcast.shared.message.Impl.VehicleSaveMessage;
import com.example.hazelcast.shared.message.Impl.VehicleUpdateMessage;
import com.example.hazelcast.shared.model.Vehicle;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by netof on 07/02/2018.
 */
@Component
public class ListenerTopic implements MessageListener<IVehicleMessage> {
    @Autowired
    VehicleRestCommandServiceClientTopic vehicleRestCommandServiceClientTopic;

    final static Logger logger = LoggerFactory.getLogger(ListenerTopic.class);


    @Override
    public void onMessage(Message<IVehicleMessage> message) {
        try{
            String topicName = String.valueOf(message.getSource());
            IVehicleMessage vehicleFromMessage = message.getMessageObject();

            logger.debug("New message for " + vehicleFromMessage.getVehicle().getVehicleId() + " on topic " + topicName);

            if(vehicleFromMessage instanceof VehicleUpdateMessage){
                vehicleRestCommandServiceClientTopic.updateVehicle(vehicleFromMessage.getVehicle());
            }

            if(vehicleFromMessage instanceof VehicleSaveMessage){
                vehicleRestCommandServiceClientTopic.save(vehicleFromMessage.getVehicle());
            }

            if(vehicleFromMessage instanceof VehicleDeleteMessage){
                vehicleRestCommandServiceClientTopic.deleteVehicle(vehicleFromMessage.getVehicle().getVehicleId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
