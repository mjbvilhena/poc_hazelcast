package com.example.hazelcast.client.controller;

import com.example.hazelcast.shared.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.hazelcast.client.service.VehicleRestQueryServiceClient;
import com.example.hazelcast.client.service_hazelcast.VehicleRestQueryServiceHazelcast;

import java.util.Collection;


/**
 * Created by netof on 11/01/2018.
 */
@RestController
@RequestMapping(value="/api/vehicles")
public class VehicleQueryRestController {

    @Autowired
    VehicleRestQueryServiceClient vehicleRestQueryServiceClient;

    @Autowired
    VehicleRestQueryServiceHazelcast vehicleRestQueryServiceHazelcast;

    /*to call list use URL: "/api/vehicles" */
    @GetMapping
    public ResponseEntity<Collection<Vehicle>> list(){
        //return ResponseEntity.status(HttpStatus.OK).body(vehicleService.listAll());
        return ResponseEntity.status(HttpStatus.OK).body(vehicleRestQueryServiceHazelcast.listAll());
    }

    @GetMapping(value="/vehicle/{vehicletId}")
    public ResponseEntity<?> getVehicleByRegistrationNumber(@PathVariable("vehicletId")Long vehicletId) throws Exception {
       // Vehicle vehicle = vehicleService.findById(vehicletId);
        Collection<Vehicle> vehicle = vehicleRestQueryServiceHazelcast.findById(vehicletId);
        return ResponseEntity.status(HttpStatus.OK).body(vehicle);
    }

}
