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
        //Nothing to do here. Changes to the data store should not be done via the Cache.
    }

    @Override
    public void storeAll(Map<Long, Vehicle> map) {
        //Nothing to do here. Changes to the data store should not be done via the Cache.
    }

    @Override
    public void delete(Long aLong) {
        //Nothing to do here. Changes to the data store should not be done via the Cache.
    }

    @Override
    public void deleteAll(Collection<Long> collection) {
        //Nothing to do here. Changes to the data store should not be done via the Cache.
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
