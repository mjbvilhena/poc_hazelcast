package com.example.hazelcast.shared.model;

import com.example.hazelcast.shared.converter.LocalDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by netof on 11/01/2018.
 */
@Entity
@Table(name="vehicle")
public class Vehicle implements Serializable{

    private static final long serialVersionUID = -1578955838465518128L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name="registration_number")
    private Long vehicleId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @NotNull(message="Registration Date is required!!!")
    @Column(name="registration_date",nullable=false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime registrationDate;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "vehicle")
    private VehicleDetails vehicleDetails;

    public Vehicle(){

    }

    public Vehicle(LocalDateTime registrationDate, VehicleDetails vehicleDetails) {
        this.registrationDate = registrationDate;
        this.vehicleDetails = vehicleDetails;
        this.vehicleDetails.setVehicle(this);
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(VehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (!vehicleId.equals(vehicle.vehicleId)) return false;
        return registrationDate.equals(vehicle.registrationDate);
    }

    @Override
    public int hashCode() {
        int result = vehicleId.hashCode();
        result = 31 * result + registrationDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", registrationDate=" + registrationDate +
                ", vehicleColour=" + vehicleDetails.getColour() +
                ", vehicleModel=" + vehicleDetails.getModel() +
                ", vehicleStatus=" + vehicleDetails.getVehicleStatus() +
                '}';
    }
}
