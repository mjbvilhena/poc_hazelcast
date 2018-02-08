package com.example.hazelcast.client.command.topic;

import com.example.hazelcast.client.command.topic.listener.ListenerTopic;
import com.example.hazelcast.shared.model.Vehicle;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.hazelcast.shared.topic.TopicNames.VEHICLES_TOPIC_DELETE;
import static com.example.hazelcast.shared.topic.TopicNames.VEHICLES_TOPIC_SAVE;
import static com.example.hazelcast.shared.topic.TopicNames.VEHICLES_TOPIC_UPDATE;

@SpringBootApplication
@EntityScan("com.example.hazelcast.shared.model")
@Configuration
public class ClientCommandTopicApplication {


	@Autowired
	private ListenerTopic listenerTopic;

	public static void main(String[] args) {
		SpringApplication.run(ClientCommandTopicApplication.class, args);
	}

	@Bean
	public ClientConfig clientConfig() throws Exception{
		ClientConfig clientConfig = new ClientConfig();
		ClientNetworkConfig clientNetworkConfig = clientConfig.getNetworkConfig();
		clientNetworkConfig.setConnectionAttemptLimit(0);//unlimited, try to recconect
		return clientConfig;
	}

	@Bean(name = "ClientCommandTopicInstance", destroyMethod = "shutdown")
	public HazelcastInstance createClientInstance(ClientConfig clientConfig) throws Exception{
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		ITopic<Vehicle> vehicleTopicDelete = client.getTopic(VEHICLES_TOPIC_DELETE);
		vehicleTopicDelete.addMessageListener(listenerTopic);

		ITopic<Vehicle> vehicleTopicUpdate = client.getTopic(VEHICLES_TOPIC_UPDATE);
		vehicleTopicUpdate.addMessageListener(listenerTopic);

		ITopic<Vehicle> vehicleTopicSave = client.getTopic(VEHICLES_TOPIC_SAVE);
		vehicleTopicSave.addMessageListener(listenerTopic);

		return client;
	}

}
