package com.example.hazelcast.client.command.topic;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EntityScan("com.example.hazelcast.shared.model")
@Configuration
public class ClientCommandTopicApplication {

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
		return HazelcastClient.newHazelcastClient(clientConfig);
	}

}
