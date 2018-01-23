package com.example.hazelcast.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.hazelcast.shared.model.*;

/**
 * Created by netof on 11/01/2018.
 */
@Repository
public interface VehicleRepositoryClient extends JpaRepository<Vehicle, Long> {


}
