package com.Bicycle.BicycleInventory.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Bicycle.BicycleInventory.security.JwtUtil;
import com.Bicycle.BicycleInventory.services.UserServices;
import com.Bicycle.BicycleInventory.Dto.UserDto;
import com.Bicycle.BicycleInventory.entity.UserEntity;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserServices userServices;
    private final JwtUtil jwt;

    public UserController(UserServices userServices, JwtUtil jwt) {
        this.userServices = userServices;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto req) {
        return ResponseEntity.ok(userServices.register(req.getEmail(), req.getPasword()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity request) {
        UserEntity user = userServices.findByEmail(request.getEmail());

        String token = jwt.generateToken(user.getEmail());

        return ResponseEntity.ok(
                Map.of(
                        "id", user.getId(),
                        "email", user.getEmail(),
                        "token", token));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userServices.findAllUsers());
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserEntity> patchUser(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        UserEntity updatedUser = userServices.patchUser(id, updates);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userServices.deleteUser(id);
        return ResponseEntity.ok("Usuario eliminado con éxito");
    }
}
