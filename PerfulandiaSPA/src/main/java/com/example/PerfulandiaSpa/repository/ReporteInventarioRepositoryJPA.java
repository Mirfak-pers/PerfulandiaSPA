package com.example.PerfulandiaSpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PerfulandiaSpa.model.ReporteInventario;

public interface ReporteInventarioRepositoryJPA extends JpaRepository<ReporteInventario, String> {
    // Métodos personalizados si los necesitas
}