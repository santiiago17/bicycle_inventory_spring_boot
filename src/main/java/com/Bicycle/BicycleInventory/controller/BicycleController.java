package com.Bicycle.BicycleInventory.controller;

import com.Bicycle.BicycleInventory.entity.BicycleEntity;
import com.Bicycle.BicycleInventory.security.JwtUtil;
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

    private boolean isValidToken(String authToken) {
        if (authToken == null || !authToken.startsWith("Bearer ")) {
            return false;
        }
        String token = authToken.replace("Bearer ", "");
        return JwtUtil.validateToken(token);
    }

    @GetMapping
    public ResponseEntity<List<BicycleEntity>> getAll(@RequestHeader("Authorization") String authToken) {
        if (!isValidToken(authToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // HTTP 401 Unauthorized
        }

        List<BicycleEntity> bicycles = services.getAll();
        return ResponseEntity.ok(bicycles); // HTTP 200
    }

    @PostMapping
    public ResponseEntity<BicycleEntity> create(@RequestBody BicycleEntity bicycle, @RequestHeader("Authorization") String authToken) {
        if (!isValidToken(authToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // HTTP 401 Unauthorized
        }

        BicycleEntity savedBicycle = services.create(bicycle);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBicycle); // HTTP 201
    }

    @PutMapping("/{id}")
    public ResponseEntity<BicycleEntity> update(@PathVariable Long id, @RequestBody BicycleEntity bicycle, @RequestHeader("Authorization") String authToken) {

        if (!isValidToken(authToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // HTTP 401 Unauthorized
        }
        else{
            try {
                BicycleEntity updatedBicycle = services.update(id, bicycle);
                return ResponseEntity.ok(updatedBicycle); // HTTP 200
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build(); // HTTP 404 si no existe
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader("Authorization") String authToken) {
        if (!isValidToken(authToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // HTTP 401 Unauthorized
        }
        else{
            try {
                services.delete(id);
                return ResponseEntity.noContent().build(); // HTTP 204 No Content
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build(); // HTTP 404 si no existe
            }
        }
    }
}
