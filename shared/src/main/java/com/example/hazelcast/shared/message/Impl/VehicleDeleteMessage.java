package com.example.hazelcast.shared.message.Impl;

import com.example.hazelcast.shared.interface_message.IVehicleMessage;
import com.example.hazelcast.shared.model.Vehicle;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by netof on 09/02/2018.
 */
public class VehicleDeleteMessage extends TopicQueueMessage implements Serializable,IVehicleMessage {

    private Vehicle vehicle;

    @Override
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public Vehicle getVehicle() {
        return vehicle;
    }
}
