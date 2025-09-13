package com.Bicycle.BicycleInventory.controller;

import com.Bicycle.BicycleInventory.entity.BicycleEntity;
import com.Bicycle.BicycleInventory.services.BicycleServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bicycles")
public class BicycleController {

    private final BicycleServices services;

    public BicycleController(BicycleServices services) {
        this.services = services;
    }

    @GetMapping
    public ResponseEntity<List<BicycleEntity>> getAll() {
        List<BicycleEntity> bicycles = services.getAll();
        return ResponseEntity.ok(bicycles); // HTTP 200
    }

    @PostMapping
    public ResponseEntity<BicycleEntity> create(@RequestBody BicycleEntity bicycle) {
        BicycleEntity savedBicycle = services.create(bicycle);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBicycle); // HTTP 201
    }

    @PutMapping("/{id}")
    public ResponseEntity<BicycleEntity> update(@PathVariable Long id, @RequestBody BicycleEntity bicycle) {
        try {
            BicycleEntity updatedBicycle = services.update(id, bicycle);
            return ResponseEntity.ok(updatedBicycle); // HTTP 200
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 si no existe
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            services.delete(id);
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 si no existe
        }
    }
}
