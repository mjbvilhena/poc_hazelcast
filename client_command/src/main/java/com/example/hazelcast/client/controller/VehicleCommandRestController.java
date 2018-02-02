package com.example.hazelcast.client.controller;

import com.example.hazelcast.client.service.VehicleRestCommandServiceClient;
import com.example.hazelcast.shared.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by netof on 29/01/2018.
 */
@RestController
@RequestMapping(value="/api/vehicles")
public class VehicleCommandRestController {

    @Autowired
    VehicleRestCommandServiceClient vehicleRestCommandServiceClient;

    @DeleteMapping(value="/vehicle/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("vehicleId")Long vehicleId){
        vehicleRestCommandServiceClient.deleteVehicle(vehicleId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/vehicle/{vehicleId}")
    public ResponseEntity<Void> updateVehicle(@RequestBody Vehicle vehicle, @PathVariable("vehicleId") Long vehicleId)throws Exception{
        vehicleRestCommandServiceClient.updateVehicle(vehicle);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody Vehicle vehicle) throws Exception {
        vehicle = vehicleRestCommandServiceClient.save(vehicle);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{vehicleId}").buildAndExpand(vehicle.getVehicleId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
