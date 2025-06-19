// filepath: [ReporteVentaService.java](http://_vscodecontentref_/5)
package com.example.PerfulandiaSpa.services;

import com.example.PerfulandiaSpa.model.ReporteVenta;
import com.example.PerfulandiaSpa.model.Sucursal;
import com.example.PerfulandiaSpa.repository.ReporteVentaRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReporteVentaService {

    @Autowired
    private ReporteVentaRepositoryJpa reporteVentaRepository;

    public ReporteVenta saveReporteVenta(ReporteVenta venta) {
        return reporteVentaRepository.save(venta);
    }

    public List<ReporteVenta> getAllReportesVenta() {
        return reporteVentaRepository.findAll();
    }

    public void deleteReporteVenta(String id) {
        reporteVentaRepository.deleteById(id);
    }

    public List<ReporteVenta> getReporteVentas(Sucursal sucursal) {
    // Implement logic to fetch reportes for the given sucursal
    return new ArrayList<>();
    }
}