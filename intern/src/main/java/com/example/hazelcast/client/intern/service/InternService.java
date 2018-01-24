package com.example.hazelcast.client.intern.service;

import com.example.hazelcast.client.intern.repository.CdcRepository;
import com.example.hazelcast.client.intern.repository.VehicleRepositoryIntern;
import com.example.hazelcast.shared.map.MapNames;
import com.example.hazelcast.shared.model.Cdc;
import com.example.hazelcast.shared.model.Vehicle;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /*populating MAP with DB values*/
    //@Scheduled(cron = "*/2 * * * * *")//every 2 seconds
 //   @Scheduled(cron = "* */1 * * * *")//every 2 seconds
    public void populateMapFromDb(){
        System.out.println("### RUNNING SCHEDULED JOB Getting CDC Data ###");

        vehiclesMap = hazelcastInstance.getMap(VIHICLES_MAP);
        List<Cdc> list = cdcRepository.findAll();
        for (Cdc cdc : list){
            if(cdc.getAction().equals("UPDATE") || cdc.getAction().equals("INSERT") || cdc.getAction().equals("DELETE")){
                Long key = cdc.getVehicle();
                Vehicle vehicle = vehicleRepositoryIntern.findOne(key);
                vehiclesMap.put(key, vehicle);
            }
        }
        System.out.println("### HAZELCAST MAP POPULATED FROM DATABASE ###");
    }


}
