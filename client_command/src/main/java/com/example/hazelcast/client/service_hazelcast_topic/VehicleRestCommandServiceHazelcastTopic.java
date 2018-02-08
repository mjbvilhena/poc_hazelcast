package com.example.hazelcast.client.service_hazelcast_topic;

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

    private ITopic<Vehicle> vehicleTopicSave = null;
    private ITopic<Vehicle> vehicleTopicUpdate = null;
    private ITopic<Vehicle> vehicleTopicDelete = null;

    private HazelcastInstance hazelcastInstance;

    @Autowired
    public VehicleRestCommandServiceHazelcastTopic(@Qualifier("ClientCommandInstance")HazelcastInstance hazelcastInstance){
        this.hazelcastInstance = hazelcastInstance;
    }

    @PostConstruct
    public void init() {
    }

    public void saveVehicle(Vehicle vehicle){
        vehicleTopicSave = hazelcastInstance.getTopic(VEHICLES_TOPIC_SAVE);
        vehicleTopicSave.publish(vehicle);
    }

    public void updateVehicle(Vehicle vehicle){
        vehicleTopicUpdate = hazelcastInstance.getTopic(VEHICLES_TOPIC_UPDATE);
        vehicleTopicUpdate.publish(vehicle);
    }

    public void deleteVehicle(Vehicle vehicle){
        vehicleTopicDelete = hazelcastInstance.getTopic(VEHICLES_TOPIC_DELETE);
        vehicleTopicDelete.publish(vehicle);
    }

    public void removeMessageListener(String message, ITopic<Vehicle> topic){
        if(null != message && null != topic){
            topic.removeMessageListener(message);
        }
    }

}
