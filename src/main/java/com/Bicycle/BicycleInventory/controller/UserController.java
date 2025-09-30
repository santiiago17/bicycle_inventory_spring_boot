package com.Bicycle.BicycleInventory.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Bicycle.BicycleInventory.security.JwtUtil;
import com.Bicycle.BicycleInventory.services.UserServices;
import com.Bicycle.BicycleInventory.Dto.UserDto;
import com.Bicycle.BicycleInventory.entity.UserEntity;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserServices authService;
    private final JwtUtil jwt;

    public UserController(UserServices authService, JwtUtil jwt) {
        this.authService = authService;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto req){
        return ResponseEntity.ok(authService.register(req.getEmail(), req.getPasword()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity request){
        UserEntity user = authService.findByEmail(request.getEmail());

        String token = jwt.generateToken(user.getEmail());

        return ResponseEntity.ok(
            Map.of(
                "id", user.getId(),
                "usuario", user.getEmail(),
                "token", token
            )
        );
    }
}
