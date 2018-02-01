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

    @DeleteMapping(value="/vehicle/{vehicletId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("vehicletId")Long vehicletId){
        vehicleRestCommandServiceClient.deleteVehicle(vehicletId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/vehicle/{vehicletId}")
    public ResponseEntity<Void> updateVehicle(@RequestBody Vehicle vehicle, @PathVariable("vehicletId") Long vehicletId)throws Exception{
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
