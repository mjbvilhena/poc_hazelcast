package com.example.hazelcast.client.service;

import com.example.hazelcast.shared.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.example.hazelcast.client.repository.VehicleRepositoryClient;

import java.util.List;

/**
 * Created by netof on 11/01/2018.
 */
@Service
public class VehicleServiceClient {

    @Autowired
    private VehicleRepositoryClient vehicleRepositoryClient;

    @Cacheable(value="vehiclesCache",unless="#result==null")
    public List<Vehicle> listAll(){
        return vehicleRepositoryClient.findAll();
    }

    @Cacheable(value="vehiclesCache",key="#vehicletId",unless="#result==null")
    public Vehicle findById(Long vehicletId) throws Exception {
        Vehicle vehicle = vehicleRepositoryClient.findOne(vehicletId);
        if(vehicle == null){
            throw new Exception("Vehicle not found!!!");
        }
        return vehicle;
    }

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

    private boolean exist(Vehicle vehicle){
        boolean exist = false;
        try {
            if(findById(vehicle.getVehicleId()) != null){
                exist  = true;
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exist;
    }

    //    @CachePut(value="vehiclesCache",key="#p0")
//    public Vehicle save(Vehicle vehicle) throws Exception{
//        if(exist(vehicle)){
//            throw new Exception("Vehicle alrady in Database!!!");
//        }else{
//            return vehicleRepository.save(vehicle);
//        }
//    }

}
