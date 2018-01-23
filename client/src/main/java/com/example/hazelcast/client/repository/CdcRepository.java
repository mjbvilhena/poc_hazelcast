package com.example.hazelcast.client.repository;

import com.example.hazelcast.shared.model.Cdc;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by netof on 16/01/2018.
 */
public interface CdcRepository extends JpaRepository<Cdc, Long> {
}
