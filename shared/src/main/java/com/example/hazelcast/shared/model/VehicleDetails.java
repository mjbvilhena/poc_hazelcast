package com.example.hazelcast.shared.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * Created by netof on 15/01/2018.
 */
@Entity
@Table(name="vehicles_details")
public class VehicleDetails implements Serializable{

    private static final long serialVersionUID = 7638924870022660679L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message="Status is required!!!")
    @Column(name="status",nullable=false)
    private String vehicleStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message="Colour is required!!!")
    @Column(name="colour",nullable=false)
    private String colour;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message="Model is required!!!")
    @Column(name= "com/example/hazelcast/shared/model",nullable=false)
    private String model;

    private Vehicle vehicle;

    public VehicleDetails(){

    }

    public VehicleDetails(String vehicleStatus, String colour, String model) {
        this.vehicleStatus = vehicleStatus;
        this.colour = colour;
        this.model = model;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Id
    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "vehicleId")
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleDetails that = (VehicleDetails) o;

        if (vehicleStatus != null ? !vehicleStatus.equals(that.vehicleStatus) : that.vehicleStatus != null)
            return false;
        if (colour != null ? !colour.equals(that.colour) : that.colour != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        return vehicle != null ? vehicle.equals(that.vehicle) : that.vehicle == null;
    }

    @Override
    public int hashCode() {
        int result = vehicleStatus != null ? vehicleStatus.hashCode() : 0;
        result = 31 * result + (colour != null ? colour.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (vehicle != null ? vehicle.hashCode() : 0);
        return result;
    }
}
