package com.Bicycle.BicycleInventory.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.Bicycle.BicycleInventory.entity.UserEntity;
import com.Bicycle.BicycleInventory.repository.UserRepository;

@Service
public class UserServices {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Registrar usuario
    public String register(String email, String pasword) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPasword(encoder.encode(pasword)); // Se guarda encriptada
        userRepository.save(user);
        return "User registered successfully";
    }

    // Validar login (sin generar token aquí)
    public boolean validateUser(String email, String pasword) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            return encoder.matches(pasword, user.getPasword());
        }
        return false;
    }

    // Buscar usuario por email
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Listar todos los usuarios
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }
    // Actualizar parcialmente un usuario
    public UserEntity patchUser(Long id, Map<String, Object> updates) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con id " + id + " no existe"));

        // Verificamos qué campos llegaron en el body y los actualizamos
        if (updates.containsKey("email")) {
            String email = (String) updates.get("email");
            if (email != null && !email.isBlank()) {
                user.setEmail(email);
            }
        }

        if (updates.containsKey("pasword")) {
            String password = (String) updates.get("pasword");
            if (password != null && !password.isBlank()) {
                user.setPasword(encoder.encode(password));
            }
        }

        return userRepository.save(user);
    }

    // Eliminar usuario por ID
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario con id " + id + " no existe");
        }
        userRepository.deleteById(id);
    }
}
