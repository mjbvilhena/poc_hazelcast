package com.example.hazelcast.client.controller;

import com.example.hazelcast.shared.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.hazelcast.client.service.VehicleServiceClient;
import com.example.hazelcast.client.service_hazelcast.VehicleServiceHazelcast;

import java.util.Collection;


/**
 * Created by netof on 11/01/2018.
 */
@RestController
@RequestMapping(value="/api/vehicles")
public class VehicleController {

    @Autowired
    VehicleServiceClient vehicleServiceClient;

    @Autowired
    VehicleServiceHazelcast vehicleServiceHazelcast;

    /*to call list use URL: "/api/vehicles" */
    @GetMapping
    public ResponseEntity<Collection<Vehicle>> list(){
        //return ResponseEntity.status(HttpStatus.OK).body(vehicleService.listAll());
        return ResponseEntity.status(HttpStatus.OK).body(vehicleServiceHazelcast.listAll());
    }

    @GetMapping(value="/vehicle/{vehicletId}")
    public ResponseEntity<?> getVehicleByRegistrationNumber(@PathVariable("vehicletId")Long vehicletId) throws Exception {
       // Vehicle vehicle = vehicleService.findById(vehicletId);
        Collection<Vehicle> vehicle = vehicleServiceHazelcast.findById(vehicletId);
        return ResponseEntity.status(HttpStatus.OK).body(vehicle);
    }

    @DeleteMapping(value="/vehicle/{vehicletId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("vehicletId")Long vehicletId){
        vehicleServiceClient.deleteVehicle(vehicletId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/vehicle/{vehicletId}")
    public ResponseEntity<Void> updateVehicle(@RequestBody Vehicle vehicle, @PathVariable("vehicletId") Long vehicletId)throws Exception{
        vehicleServiceClient.updateVehicle(vehicle);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping
//    public ResponseEntity<Void> save(@Valid @RequestBody Vehicle vehicle) throws Exception {
//        vehicle = vehicleService.save(vehicle);
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{vehicleId}").buildAndExpand(vehicle.getId()).toUri();
//
//        return ResponseEntity.created(uri).build();
//    }

}
