package com.example.hazelcast.client.intern;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@SpringBootApplication
@EntityScan("com.example.hazelcast.shared.model")
@Configuration
@EnableScheduling
public class InternApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternApplication.class, args);
	}

	@Bean
	public ClientConfig clientConfig() throws Exception{
		ClientConfig clientConfig = new ClientConfig();
		ClientNetworkConfig clientNetworkConfig = clientConfig.getNetworkConfig();
		clientNetworkConfig.setConnectionAttemptLimit(0);//unlimited, try to recconect
		return clientConfig;
	}

	@Bean(name = "InternInstance", destroyMethod = "shutdown")
	public HazelcastInstance createClientInstance(ClientConfig clientConfig) throws Exception{
		return HazelcastClient.newHazelcastClient(clientConfig);
	}
}
