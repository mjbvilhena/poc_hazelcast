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
public class VehicleRestQueryServiceClient {

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

}
