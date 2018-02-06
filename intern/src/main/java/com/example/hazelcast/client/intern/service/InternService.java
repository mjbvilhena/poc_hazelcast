package com.example.hazelcast.client.intern.service;

import com.example.hazelcast.client.intern.repository.CdcRepository;
import com.example.hazelcast.client.intern.repository.VehicleRepositoryIntern;
import com.example.hazelcast.shared.map.MapNames;
import com.example.hazelcast.shared.model.Cdc;
import com.example.hazelcast.shared.model.Vehicle;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by netof on 24/01/2018.
 */
@Service
public class InternService implements MapNames{

    private HazelcastInstance hazelcastInstance;
    private IMap<Long, Vehicle> vehiclesMap;

    @Autowired
    CdcRepository cdcRepository;

    @Autowired
    VehicleRepositoryIntern vehicleRepositoryIntern;

    @Autowired
    public InternService(@Qualifier("InternInstance")HazelcastInstance hazelcastInstance){
        this.hazelcastInstance = hazelcastInstance;
    }

    @PostConstruct
    public void init(){
        //populated from storege Node App
        vehiclesMap = hazelcastInstance.getMap(VEHICLES_MAP);
    }

    /*populating MAP with DB values*/
    @Scheduled(cron = "*/55 * * * * *")//every 2 seconds
    public void populateMapFromDb(){
        System.out.println("### RUNNING SCHEDULED JOB Getting CDC Data ###");

        vehiclesMap = hazelcastInstance.getMap(VEHICLES_MAP);
        List<Cdc> list = cdcRepository.findAll();
        for (Cdc cdc : list){
            if(cdc.getProcessed_to_cache() == null){
                Long key = cdc.getVehicle();
                if(cdc.getAction().equals("UPDATE") || cdc.getAction().equals("INSERT")){
                    Vehicle vehicle = vehicleRepositoryIntern.findOne(key);
                    if(vehicle != null){
                        vehiclesMap.put(key, vehicle);
                        cdc.setProcessed_to_cache(LocalDateTime.now());
                        cdcRepository.save(cdc);
                        System.out.println("######### PUT " + key + " #########");
                    }
                }else if(cdc.getAction().equals("DELETE")){
                    vehiclesMap.delete(key);
                    cdc.setProcessed_to_cache(LocalDateTime.now());
                    cdcRepository.save(cdc);
                    System.out.println("######### DEL " + key + " #########");
                }
            }
        }
        System.out.println("### HAZELCAST MAP SYNCHRONIZED WITH DATABASE ###");
    }

}
