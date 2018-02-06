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

    private ITopic<Vehicle> vehicleTopic = null;
    private HazelcastInstance hazelcastInstance;

    @Autowired
    public VehicleRestCommandServiceHazelcastTopic(@Qualifier("ClientCommandInstance")HazelcastInstance hazelcastInstance){
        this.hazelcastInstance = hazelcastInstance;
    }

    @PostConstruct
    public void init() {
        vehicleTopic = hazelcastInstance.getTopic(VEHICLES_TOPIC);
    }

    public void saveOrUpdateVehicle(Vehicle vehicle)throws Exception{
        vehicleTopic.publish(vehicle);

    }

}
