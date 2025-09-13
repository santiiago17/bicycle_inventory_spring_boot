package com.Bicycle.BicycleInventory.Dto;

// import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class BicycleDto {
    private Long id;
    private String brand;
    private String model;
    private String primary_color;
    private String secondary_color;
    private String bike_type;
    private Double price;
    private int stock;
    // private Timestamp created_at;   
}
