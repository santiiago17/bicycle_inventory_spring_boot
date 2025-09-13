package com.Bicycle.BicycleInventory.entity;

// import java.sql.Time;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bicycles")
@Data

public class BicycleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String primary_color;
    private String secondary_color;
    private String bike_type;
    private Double price;
    private int stock;
    private Timestamp created_at;
}



