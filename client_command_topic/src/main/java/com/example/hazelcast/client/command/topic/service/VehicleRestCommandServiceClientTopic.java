package com.example.hazelcast.client.command.topic.service;

import com.example.hazelcast.client.command.topic.repository.VehicleRepositoryClientTopic;
import com.example.hazelcast.shared.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

/**
 * Created by netof on 11/01/2018.
 */
@Service
public class VehicleRestCommandServiceClientTopic{

    @Autowired
    private VehicleRepositoryClientTopic vehicleRepositoryClientTopic;


    /*If for some reason the names are not available (ex: no debug information),
     the parameter names are also available under the p<#arg> where #arg stands for the parameter index (starting from 0).
     https://stackoverflow.com/questions/14197359/spring-cache-abstraction-vs-interfaces-vs-key-param-null-key-returned-for-cach*/
    @CachePut(value="vehiclesCache",key="#p0")
    public void updateVehicle(Vehicle vehicle)throws Exception{
        if(exist(vehicle)){
            vehicleRepositoryClientTopic.save(vehicle);
        }else{
            throw new Exception("Vehicle not found!!!");
        };

    }

    @CacheEvict(value="vehiclesCache",key="#p0")
    public void deleteVehicle(Long vehicleId) {

        vehicleRepositoryClientTopic.delete(vehicleId);
    }

    @CachePut(value="vehiclesCache",key="#p0")
    public Vehicle save(Vehicle vehicle) throws Exception{
        return vehicleRepositoryClientTopic.save(vehicle);
    }

    private boolean exist(Vehicle vehicle){
        boolean exist = false;
        try {
            if(vehicleRepositoryClientTopic.findOne(vehicle.getVehicleId()) != null){
                exist  = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exist;
    }

}
