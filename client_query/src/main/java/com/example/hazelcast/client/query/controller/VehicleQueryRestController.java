package com.example.hazelcast.client.query.controller;
import com.example.hazelcast.client.query.service_hazelcast.VehicleRestQueryServiceHazelcast;
import com.example.hazelcast.shared.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


/**
 * Created by netof on 11/01/2018.
 */
@RestController
@RequestMapping(value="/api/vehicles")
public class VehicleQueryRestController {


    @Autowired
    VehicleRestQueryServiceHazelcast vehicleRestQueryServiceHazelcast;

    /*to call list use URL: "/api/vehicles" */
    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<Collection<Vehicle>> list(){
        //return ResponseEntity.status(HttpStatus.OK).body(vehicleService.listAll());
        return ResponseEntity.status(HttpStatus.OK).body(vehicleRestQueryServiceHazelcast.listAll());
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value="/vehicle/{vehicleId}")
    public ResponseEntity<?> getVehicleByRegistrationNumber(@PathVariable("vehicleId")Long vehicleId) throws Exception {
       // Vehicle vehicle = vehicleService.findById(vehicleId);
        Collection<Vehicle> vehicle = vehicleRestQueryServiceHazelcast.findById(vehicleId);
        return ResponseEntity.status(HttpStatus.OK).body(vehicle);
    }

}
