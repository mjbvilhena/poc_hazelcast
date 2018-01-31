package com.example.hazelcast.client.dataLoader;

import com.example.hazelcast.client.repository.VehicleRepositoryClient;

import com.example.hazelcast.shared.model.Cdc;
import com.example.hazelcast.shared.model.Vehicle;
import com.example.hazelcast.shared.model.VehicleDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * Created by netof on 16/01/2018.
 */
@Component
public class InitialDataLoader implements ApplicationRunner {

    private VehicleRepositoryClient vehicleRepositoryClient;
    private static Vehicle vehicle;
    private static VehicleDetails vehicleDetails;
    private static LocalDateTime currentDate = LocalDateTime.now();

    @Autowired
    public InitialDataLoader(VehicleRepositoryClient vehicleRepositoryClient) {
        this.vehicleRepositoryClient = vehicleRepositoryClient;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        //clearObject(vehicleRepositoryClient);
        //populateVehicle(vehicleRepositoryClient);

    }

    /*Populate Vehicle Object*/
    public static void populateVehicle(VehicleRepositoryClient vehicleRepositoryClient){

        //Status 1 = taxed, 0 = not taxed

        vehicle = new Vehicle(currentDate, new VehicleDetails("1","White","BMW"));
        vehicleRepositoryClient.save(vehicle);

        vehicle = new Vehicle(currentDate, new VehicleDetails("0","Black","Fiat"));
        vehicleRepositoryClient.save(vehicle);

        vehicle = new Vehicle(currentDate, new VehicleDetails("1","Green","VW"));
        vehicleRepositoryClient.save(vehicle);

        vehicle = new Vehicle(currentDate, new VehicleDetails("0","Red","Volvo"));
        vehicleRepositoryClient.save(vehicle);

        vehicle = new Vehicle(currentDate, new VehicleDetails("1","Purple","Kia"));
        vehicleRepositoryClient.save(vehicle);

    }

    public static void clearObject(VehicleRepositoryClient vehicleRepositoryClient){
        vehicle = new Vehicle();
        vehicleDetails = new VehicleDetails();
    }

}


