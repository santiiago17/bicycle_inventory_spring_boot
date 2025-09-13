package com.Bicycle.BicycleInventory.repository;

import com.Bicycle.BicycleInventory.entity.BicycleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BicycleRepository extends JpaRepository<BicycleEntity, Long> {

}
