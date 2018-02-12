package com.example.hazelcast.shared.message.Impl;

import com.example.hazelcast.shared.interface_message.ITopicQueueMessage;

import java.time.LocalDateTime;
import java.util.UUID;

public class TopicQueueMessage implements ITopicQueueMessage{

    private final UUID id;
    private final LocalDateTime dateTime;

    public TopicQueueMessage() {
        this.id = UUID.randomUUID();
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public LocalDateTime getDataTime() {
        return this.dateTime;
    }
}
