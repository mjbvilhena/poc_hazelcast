package com.example.hazelcast.client.command.topic;

import com.example.hazelcast.shared.model.Vehicle;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

/**
 * Created by netof on 07/02/2018.
 */
public class ListenerTopicTest implements MessageListener<Vehicle> {

    @Override
    public void onMessage(Message<Vehicle> message) {
        Vehicle vehicleFromTopic = message.getMessageObject();
        long publishTime = message.getPublishTime();
        System.out.println( "Message received = " + vehicleFromTopic.getRegistrationDate());
        System.out.println( "Publish Time = " + publishTime);

    }
}
