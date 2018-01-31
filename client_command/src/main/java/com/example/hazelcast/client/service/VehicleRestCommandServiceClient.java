package com.example.hazelcast.client.service;

import com.example.hazelcast.client.repository.VehicleRepositoryClient;
import com.example.hazelcast.shared.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by netof on 11/01/2018.
 */
@Service
public class VehicleRestCommandServiceClient {

    @Autowired
    private VehicleRepositoryClient vehicleRepositoryClient;


    /*If for some reason the names are not available (ex: no debug information),
     the parameter names are also available under the p<#arg> where #arg stands for the parameter index (starting from 0).
     https://stackoverflow.com/questions/14197359/spring-cache-abstraction-vs-interfaces-vs-key-param-null-key-returned-for-cach*/
    @CachePut(value="vehiclesCache",key="#p0")
    public void updateVehicle(Vehicle vehicle)throws Exception{
        if(exist(vehicle)){
            vehicleRepositoryClient.save(vehicle);
        }else{
            throw new Exception("Vehicle not found!!!");
        };

    }

    @CacheEvict(value="vehiclesCache",key="#p0")
    public void deleteVehicle(Long vehicleId) {

        vehicleRepositoryClient.delete(vehicleId);
    }

    //    @CachePut(value="vehiclesCache",key="#p0")
//    public Vehicle save(Vehicle vehicle) throws Exception{
//        if(exist(vehicle)){
//            throw new Exception("Vehicle alrady in Database!!!");
//        }else{
//            return vehicleRepository.save(vehicle);
//        }
//    }

    private boolean exist(Vehicle vehicle){
        boolean exist = false;
        try {
            if(vehicleRepositoryClient.findOne(vehicle.getVehicleId()) != null){
                exist  = true;
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exist;
    }

}
