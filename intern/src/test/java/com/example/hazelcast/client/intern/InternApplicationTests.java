package com.example.hazelcast.client.intern;

import com.example.hazelcast.client.intern.repository.VehicleRepositoryIntern;
import com.example.hazelcast.shared.model.Vehicle;
import com.example.hazelcast.shared.model.VehicleDetails;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InternApplicationTests {

	@Autowired
	VehicleRepositoryIntern vehicleRepositoryIntern;

	private static Vehicle vehicle;
	private static VehicleDetails vehicleDetails;
	private static LocalDateTime currentDate;

	@Before
	public void init(){
		vehicle = new Vehicle();
		vehicleDetails = new VehicleDetails();
		currentDate = LocalDateTime.now();
	}

	@Test
	public void insertVehicleData() {

	    vehicle = new Vehicle(currentDate, new VehicleDetails("0","Yellow-black","TestInsert2"));
		vehicleRepositoryIntern.save(vehicle);

        Vehicle vehicleFound = vehicleRepositoryIntern.findOne(vehicle.getVehicleId());
        assertThat(vehicleFound.getVehicleId()).isEqualTo(vehicleFound.getVehicleId());

	}

    @Test
    public void updateVehicleData() {
	    List<Vehicle> list = vehicleRepositoryIntern.findAll();
	    Long idVehicle = null;
	    for(Vehicle v : list){
	        vehicleDetails = new VehicleDetails("1","COLOUR-UPDATE","TestUpdate");
	        vehicleDetails.setVehicle(v);
            v.setVehicleDetails(vehicleDetails);
            vehicleRepositoryIntern.save(v);
            idVehicle = v.getVehicleId();
	        break;
        }
        Vehicle vehicle = vehicleRepositoryIntern.findOne(idVehicle);
        Assert.assertEquals(vehicle.getVehicleDetails().getColour(), "COLOUR-UPDATE");

    }


}
