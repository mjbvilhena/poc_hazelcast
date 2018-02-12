package com.example.hazelcast.client.command.topic;

import com.example.hazelcast.client.command.topic.listener.VehicleTopicListener;
import com.example.hazelcast.shared.interface_message.IVehicleMessage;
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

import static com.example.hazelcast.shared.topic.TopicNames.*;

@SpringBootApplication
@EntityScan("com.example.hazelcast.shared")
@Configuration
public class ClientCommandTopicApplication {


	@Autowired
	private VehicleTopicListener vehicleTopicListener;

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

		ITopic<IVehicleMessage> vehicleTopic = client.getTopic(VEHICLES_TOPIC);
		vehicleTopic.addMessageListener(vehicleTopicListener);

		return client;
	}

}
