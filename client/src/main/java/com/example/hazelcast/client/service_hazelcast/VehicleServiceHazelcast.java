package com.example.hazelcast.client.service_hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;
import com.example.hazelcast.shared.map.MapNames;
import com.example.hazelcast.shared.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.example.hazelcast.client.service.VehicleServiceClient;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

/**
 * Created by netof on 18/01/2018.
 */
@Service
public class VehicleServiceHazelcast implements MapNames {

    private HazelcastInstance hazelcastInstance;
    private IMap<Long, Vehicle> vehiclesMap;

    @Autowired
    VehicleServiceClient vehicleServiceClient;

    @Autowired
    public VehicleServiceHazelcast(@Qualifier("ClientInstance")HazelcastInstance hazelcastInstance){
        this.hazelcastInstance = hazelcastInstance;
    }

    @PostConstruct
    public void init(){
       vehiclesMap = hazelcastInstance.getMap(VIHICLES_MAP);//populated from storege Node App
    }

    /*actions*/
    public Collection<Vehicle> listAll(){
        return vehiclesMap.values();
    }

    public Collection<Vehicle> findById(Long vehicleId){
        SqlPredicate sqlPredicate = new SqlPredicate("vehicleId = " + vehicleId);
        return vehiclesMap.values(sqlPredicate);
    }

    /*populating MAP with DB values*/
    //@Scheduled(cron = "*/2 * * * * *")//every 2 seconds
    @Scheduled(cron = "* */5 * * * *")//every 2 seconds
    public void populateMapFromDb(){
        System.out.println("### RUNNING SCHEDULED JOB ###");

        vehiclesMap = hazelcastInstance.getMap(VIHICLES_MAP);
        List<Vehicle> vehicles = vehicleServiceClient.listAll();
        for (Vehicle vehicle : vehicles){
            Long key = vehicle.getVehicleId();
            vehiclesMap.put(key, vehicle);
        }
        System.out.println("### HAZELCAST MAP POPULATED FROM DATABASE ###");
    }

}
