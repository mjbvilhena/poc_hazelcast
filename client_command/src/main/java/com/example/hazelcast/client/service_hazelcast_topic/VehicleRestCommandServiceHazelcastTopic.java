package com.example.hazelcast.client.service_hazelcast_topic;

import com.example.hazelcast.shared.interface_message.IVehicleMessage;
import com.example.hazelcast.shared.message.Impl.VehicleDeleteMessage;
import com.example.hazelcast.shared.message.Impl.VehicleSaveMessage;
import com.example.hazelcast.shared.message.Impl.VehicleUpdateMessage;
import com.example.hazelcast.shared.model.Vehicle;
import com.example.hazelcast.shared.topic.TopicNames;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
 * Created by netof on 18/01/2018.
 */
@Service
public class VehicleRestCommandServiceHazelcastTopic implements TopicNames {

    private VehicleUpdateMessage vehicleUpdateMessage;
    private VehicleSaveMessage vehicleSaveMessage;
    private VehicleDeleteMessage vehicleDeleteMessage;
    private ITopic<IVehicleMessage> vehicleTopic = null;
    private HazelcastInstance hazelcastInstance;

    @Autowired
    public VehicleRestCommandServiceHazelcastTopic(@Qualifier("ClientCommandInstance")HazelcastInstance hazelcastInstance){
        this.hazelcastInstance = hazelcastInstance;
    }

    @PostConstruct
    public void init() {
        vehicleTopic = hazelcastInstance.getTopic(VEHICLES_TOPIC);
        vehicleUpdateMessage = new VehicleUpdateMessage();
        vehicleSaveMessage = new VehicleSaveMessage();
        vehicleDeleteMessage = new VehicleDeleteMessage();
    }

    public void saveVehicle(Vehicle vehicle){
        vehicleSaveMessage.setVehicle(vehicle);
        vehicleTopic.publish(vehicleSaveMessage);
    }

    public void updateVehicle(Vehicle vehicle){
        vehicleUpdateMessage.setVehicle(vehicle);
        vehicleTopic.publish(vehicleUpdateMessage);
    }

    public void deleteVehicle(Vehicle vehicle){
        vehicleDeleteMessage.setVehicle(vehicle);
        vehicleTopic.publish(vehicleDeleteMessage);
    }

}
