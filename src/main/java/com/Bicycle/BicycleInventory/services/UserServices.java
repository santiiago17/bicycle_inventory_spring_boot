package com.Bicycle.BicycleInventory.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.Bicycle.BicycleInventory.entity.UserEntity;
import com.Bicycle.BicycleInventory.repository.UserRepository;
import com.Bicycle.BicycleInventory.security.JwtUtil;

@Service
public class UserServices {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(String email, String pasword){
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPasword(encoder.encode(pasword));
        userRepository.save(user);
        return "User registered successfully";
    }

    public Optional<String> login(String email, String pasword){
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (encoder.matches(pasword, user.getPasword())) {
                String token = JwtUtil.generateToken(email);
                return Optional.of(token);
            }
        }
        return Optional.empty();
    }

    public UserEntity findByEmail(String email){
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
