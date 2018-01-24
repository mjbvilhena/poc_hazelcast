package com.example.hazelcast.client.intern.repository;

import com.example.hazelcast.shared.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by netof on 24/01/2018.
 */
@Repository
public interface VehicleRepositoryIntern extends JpaRepository<Vehicle,Long> {

}
