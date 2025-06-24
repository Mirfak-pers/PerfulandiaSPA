package com.example.PerfulandiaSpa.services;

import com.example.PerfulandiaSpa.model.Cupon;
import com.example.PerfulandiaSpa.repository.CuponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    public Cupon saveCupon(Cupon cupon) {
        return cuponRepository.save(cupon);
    }

    public List<Cupon> getAllCupones() {
        return cuponRepository.findAll();
    }

    public Optional<Cupon> getCuponById(Long id) {
        return cuponRepository.findById(id);
    }

    public void deleteCupon(Long id) {
        cuponRepository.deleteById(id);
    }
    
}