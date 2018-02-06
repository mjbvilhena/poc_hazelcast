package com.example.hazelcast.storage;

import com.example.hazelcast.shared.model.Vehicle;
import com.example.hazelcast.shared.model.VehicleDetails;
import com.example.hazelcast.storage.repository.VehicleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageApplicationTests {

	@Autowired
	VehicleRepository vehicleRepository;

	private static Vehicle vehicle;
	private static VehicleDetails vehicleDetails;
	private static Vehicle vehicleFound;
	private static LocalDateTime currentDate;

	@Before
	public void init(){
		vehicle = new Vehicle();
		vehicleDetails = new VehicleDetails();
		currentDate = LocalDateTime.now();
	}

	@Test
	public void insertVehicleData() {
		//Status 1 = taxed, 0 = not taxed

		vehicle = new Vehicle(currentDate, new VehicleDetails("1","KNIGHTFALL","SBT"));
		vehicleRepository.save(vehicle);
		vehicleFound = vehicleRepository.findOne(vehicle.getVehicleId());
		assertThat(vehicleFound.getVehicleId()).isEqualTo(vehicleFound.getVehicleId());
		clearObject();

		vehicle = new Vehicle(currentDate, new VehicleDetails("1","White","BMW"));
		vehicleRepository.save(vehicle);
		vehicleFound = vehicleRepository.findOne(vehicle.getVehicleId());
		assertThat(vehicleFound.getVehicleId()).isEqualTo(vehicleFound.getVehicleId());
		clearObject();

		vehicle = new Vehicle(currentDate, new VehicleDetails("0","Black","Fiat"));
		vehicleRepository.save(vehicle);
		vehicleFound = vehicleRepository.findOne(vehicle.getVehicleId());
		assertThat(vehicleFound.getVehicleId()).isEqualTo(vehicleFound.getVehicleId());
		clearObject();

		vehicle = new Vehicle(currentDate, new VehicleDetails("1","Green","VW"));
		vehicleRepository.save(vehicle);
		vehicleFound = vehicleRepository.findOne(vehicle.getVehicleId());
		assertThat(vehicleFound.getVehicleId()).isEqualTo(vehicleFound.getVehicleId());
		clearObject();

		vehicle = new Vehicle(currentDate, new VehicleDetails("0","Red","Volvo"));
		vehicleRepository.save(vehicle);
		vehicleFound = vehicleRepository.findOne(vehicle.getVehicleId());
		assertThat(vehicleFound.getVehicleId()).isEqualTo(vehicleFound.getVehicleId());
		clearObject();

		vehicle = new Vehicle(currentDate, new VehicleDetails("1","Purple","Kia"));
		vehicleRepository.save(vehicle);
		vehicleFound = vehicleRepository.findOne(vehicle.getVehicleId());
		assertThat(vehicleFound.getVehicleId()).isEqualTo(vehicleFound.getVehicleId());
		clearObject();

	}

//	@Test
//	public void updateVehicleData() {
//		List<Vehicle> list = vehicleRepository.findAll();
//		Long idVehicle = null;
//		for(Vehicle v : list){
//			vehicleDetails = new VehicleDetails("1","MARLYO34","BARDOBOLA");
//			vehicleDetails.setVehicle(v);
//			v.setVehicleDetails(vehicleDetails);
//			vehicleRepository.save(v);
//			idVehicle = v.getVehicleId();
//			break;
//		}
//		Vehicle vehicle = vehicleRepository.findOne(idVehicle);
//		Assert.assertEquals(vehicle.getVehicleDetails().getColour(), "MARLYO34");
//
//	}

	public static void clearObject(){
		vehicle = new Vehicle();
		vehicleDetails = new VehicleDetails();
	}


}

