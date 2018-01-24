package com.example.hazelcast.storage.storage;

import com.example.hazelcast.storage.repository.VehicleRepository;
import com.hazelcast.core.MapStore;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.example.hazelcast.shared.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by netof on 18/01/2018.
 */
@Service
public class VehiclesMapStore implements MapStore<Long, Vehicle>{

    private VehicleRepository vehicleRepository;

    @Autowired
    public VehiclesMapStore(VehicleRepository vehicleRepository){
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void store(Long aLong, Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    @Override
    public void storeAll(Map<Long, Vehicle> map) {
        vehicleRepository.save(map.values());
    }

    @Override
    public void delete(Long aLong) {
        Vehicle vehicle = load(aLong);
        vehicleRepository.delete(vehicle);
    }

    @Override
    public void deleteAll(Collection<Long> collection) {
        List<Vehicle> vehicles = (List<Vehicle>) vehicleRepository.findAll(collection);
        vehicleRepository.delete(vehicles);
    }

    @Override
    public Vehicle load(Long aLong) {
        return vehicleRepository.findOne(aLong);
    }

    @Override
    public Map<Long, Vehicle> loadAll(Collection<Long> collection) {
        List<Vehicle> vehicles = (List<Vehicle>) vehicleRepository.findAll(collection);
        return StreamSupport.stream(vehicles.spliterator(),false)
                .collect(Collectors.toMap(Vehicle::getVehicleId, Function.identity()));
    }

    @Override
    public Iterable<Long> loadAllKeys() {
        List<Vehicle> vehicles = (List<Vehicle>) vehicleRepository.findAll();
        return StreamSupport.stream(vehicles.spliterator(),false)
                .map(Vehicle::getVehicleId).collect(Collectors.toList());
    }
}
