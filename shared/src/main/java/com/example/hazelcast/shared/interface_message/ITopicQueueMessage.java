package com.example.hazelcast.shared.interface_message;

import com.example.hazelcast.shared.model.Vehicle;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by netof on 09/02/2018.
 */
public interface ITopicQueueMessage {
    UUID getId();
    LocalDateTime getDataTime();
}
