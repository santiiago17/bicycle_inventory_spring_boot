package com.Bicycle.BicycleInventory.services;

import com.Bicycle.BicycleInventory.entity.BicycleEntity;
import com.Bicycle.BicycleInventory.repository.BicycleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BicycleServices {

    private final BicycleRepository repository;

    public BicycleServices(BicycleRepository repository) {
        this.repository = repository;
    }

    // Obtener todas las bicicletas
    public List<BicycleEntity> getAll() {
        return repository.findAll();
    }

    // Crear una nueva bicicleta
    public BicycleEntity create(BicycleEntity bicycle) {
        return repository.save(bicycle);
    }

    // Actualizar bicicleta existente
    public BicycleEntity update(Long id, BicycleEntity bicycle) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setBrand(bicycle.getBrand());
                    existing.setModel(bicycle.getModel());
                    existing.setPrimary_color(bicycle.getPrimary_color());
                    existing.setSecondary_color(bicycle.getSecondary_color());
                    existing.setBike_type(bicycle.getBike_type());
                    existing.setPrice(bicycle.getPrice());
                    existing.setStock(bicycle.getStock());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Bicicleta con id " + id + " no encontrada"));
    }

    // Eliminar bicicleta
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Bicicleta con id " + id + " no encontrada");
        }
        repository.deleteById(id);
    }
}
