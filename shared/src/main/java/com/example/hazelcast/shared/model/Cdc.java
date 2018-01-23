package com.example.hazelcast.shared.model;

import com.example.hazelcast.shared.converter.LocalDateTimeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by netof on 16/01/2018.
 */
@Entity
@Table(name="cdc")
public class Cdc implements Serializable{

    private static final long serialVersionUID = 4967000011954561353L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cdc_seq_gen")
    @SequenceGenerator(name = "cdc_seq_gen", sequenceName = "cdc_id_seq")
    private Long id;

    private Long vehicleId;

    @Column(name="table_description")
    private String tableDescription;

    @Column(name="action_description")
    private String action;

    @Column(name="db_date",nullable=false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime db_date;

    @Column(name="processed_to_cache")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime processed_to_cache;

    public Cdc(){

    }

    public Cdc(Long vehicleId, String tableDescription, String action, LocalDateTime db_date, LocalDateTime processed_to_cache) {
        this.vehicleId = vehicleId;
        this.tableDescription = tableDescription;
        this.action = action;
        this.db_date = db_date;
        this.processed_to_cache = processed_to_cache;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehicle() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getTableDescription() {
        return tableDescription;
    }

    public void setTableDescription(String tableDescription) {
        this.tableDescription = tableDescription;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getDb_date() {
        return db_date;
    }

    public void setDb_date(LocalDateTime db_date) {
        this.db_date = db_date;
    }

    public LocalDateTime getProcessed_to_cache() {
        return processed_to_cache;
    }

    public void setProcessed_to_cache(LocalDateTime processed_to_cache) {
        this.processed_to_cache = processed_to_cache;
    }
}
