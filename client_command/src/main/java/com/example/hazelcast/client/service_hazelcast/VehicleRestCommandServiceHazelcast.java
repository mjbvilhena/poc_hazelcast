package com.example.hazelcast.client.service_hazelcast;

import com.example.hazelcast.shared.map.MapNames;
import com.example.hazelcast.shared.model.Vehicle;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * Created by netof on 18/01/2018.
 */
@Service
public class VehicleRestCommandServiceHazelcast implements MapNames {

    private HazelcastInstance hazelcastInstance;
    private IMap<Long, Vehicle> vehiclesMap;

    @Autowired
    public VehicleRestCommandServiceHazelcast(@Qualifier("ClientCommandInstance")HazelcastInstance hazelcastInstance){
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
//    @Scheduled(cron = "* */5 * * * *")//every 2 seconds
//    public void populateMapFromDb(){
//        System.out.println("### RUNNING SCHEDULED JOB ###");
//
//        vehiclesMap = hazelcastInstance.getMap(VIHICLES_MAP);
//        List<Vehicle> vehicles = vehicleServiceClient.listAll();
//        for (Vehicle vehicle : vehicles){
//            Long key = vehicle.getVehicleId();
//            vehiclesMap.put(key, vehicle);
//        }
//        System.out.println("### HAZELCAST MAP POPULATED FROM DATABASE ###");
//    }

}
