package com.example.hazelcast.client.service_hazelcast_topic;


import com.example.hazelcast.client.command.topic.listener.ListenerTopic;
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
    private String message = "";

    @Autowired
    private ListenerTopic listenerTopic;


    @Autowired
    public VehicleRestCommandServiceHazelcastTopic(@Qualifier("ClientCommandInstance")HazelcastInstance hazelcastInstance){
        this.hazelcastInstance = hazelcastInstance;
    }

    @PostConstruct
    public void init() {

    }

    public void saveVehicle(Vehicle vehicle){
        try{
            vehicleTopicSave = hazelcastInstance.getTopic(VEHICLES_TOPIC_SAVE);
            message = vehicleTopicSave.addMessageListener(listenerTopic);
            vehicleTopicSave.publish(vehicle);
        }finally {
            removeMessageListener(message,vehicleTopicSave);
        }
    }

    public void updateVehicle(Vehicle vehicle){
        try{
            vehicleTopicUpdate = hazelcastInstance.getTopic(VEHICLES_TOPIC_UPDATE);
            message = vehicleTopicUpdate.addMessageListener(listenerTopic);
            vehicleTopicUpdate.publish(vehicle);
        }finally {
            removeMessageListener(message,vehicleTopicUpdate);
        }
    }

    public void deleteVehicle(Vehicle vehicle){
        try{
            vehicleTopicDelete = hazelcastInstance.getTopic(VEHICLES_TOPIC_DELETE);
            message = vehicleTopicDelete.addMessageListener(listenerTopic);
            vehicleTopicDelete.publish(vehicle);
        }finally {
            removeMessageListener(message,vehicleTopicDelete);
        }
    }

    public void removeMessageListener(String message, ITopic<Vehicle> topic){
        if(null != message && null != topic){
            topic.removeMessageListener(message);
        }
    }

}
