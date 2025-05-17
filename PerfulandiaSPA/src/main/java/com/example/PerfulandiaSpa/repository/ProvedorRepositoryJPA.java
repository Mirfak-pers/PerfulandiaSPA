package com.example.PerfulandiaSpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PerfulandiaSpa.model.Provedor;

public interface ProvedorRepositoryJPA extends JpaRepository<Provedor, Integer> {
    Provedor findById(int id);
}
