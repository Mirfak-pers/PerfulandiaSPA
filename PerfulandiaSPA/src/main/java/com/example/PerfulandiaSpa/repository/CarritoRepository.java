package com.example.PerfulandiaSpa.repository;

import com.example.PerfulandiaSpa.model.Carrito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CarritoRepository {
    private final Map<Long, Carrito> carritos = new HashMap<>();

    @Autowired
    private CarritoRepositoryJpa carritoRepositoryjpa;

    public Carrito save(Carrito carrito) {
        return carritoRepositoryjpa.save(carrito);
    }

    public Optional<Carrito> findByUsuarioId(Long usuarioId) {
        return Optional.ofNullable(carritos.get(usuarioId));
    }

    public void deleteByUsuarioId(Long usuarioId) {
        carritos.remove(usuarioId);
    }
}