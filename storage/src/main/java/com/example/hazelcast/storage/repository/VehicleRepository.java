package com.example.hazelcast.storage.repository;

import com.example.hazelcast.shared.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by netof on 21/01/2018.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
