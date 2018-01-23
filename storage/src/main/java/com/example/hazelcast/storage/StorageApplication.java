package com.example.hazelcast.storage;

import com.example.hazelcast.storage.storage.VehiclesMapStore;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import com.hazelcast.config.Config;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@EntityScan("com.example.hazelcast.shared.model")// it will scan your entity shared package and will be managed by Spring
@Configuration
public class StorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageApplication.class, args);
	}

    @Bean(destroyMethod = "shutdown")
    public HazelcastInstance createStorageInstance(@Qualifier("StorageConfig") Config config) throws Exception{
        return Hazelcast.newHazelcastInstance(config);
    }

	@Bean(name = "StorageConfig")
	public Config config(VehiclesMapStore vehiclesMapStore) throws Exception {
		Config config = new Config();
		MapConfig vehicleMapConfig = new MapConfig();
		MapStoreConfig vehicleMapStoreConfig = new MapStoreConfig();
		vehicleMapStoreConfig.setImplementation(vehiclesMapStore);
        vehicleMapStoreConfig.setWriteDelaySeconds(2);
		vehicleMapConfig.setMapStoreConfig(vehicleMapStoreConfig);
		vehicleMapConfig.setName("vehicles");

		//add vehicles com.example.hazelcast.shared.map config to storage config
		config.addMapConfig(vehicleMapConfig);
		return config;
	}
}
