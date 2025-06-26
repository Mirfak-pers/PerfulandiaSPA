package com.example.PerfulandiaSpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PerfulandiaSpa.model.ReporteVenta;

public interface ReporteVentaRepositoryJPA extends JpaRepository<ReporteVenta, String> {
    // Métodos personalizados si los necesitas
}