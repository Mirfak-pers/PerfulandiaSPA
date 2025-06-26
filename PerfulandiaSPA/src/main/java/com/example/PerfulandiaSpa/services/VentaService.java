// filepath: [VentaService.java](http://_vscodecontentref_/2)
package com.example.PerfulandiaSpa.services;


import com.example.PerfulandiaSpa.model.Venta;
import com.example.PerfulandiaSpa.repository.VentaRepository;
import com.example.PerfulandiaSpa.repository.VentaRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class VentaService {

    @Autowired
    private VentaRepositoryJPA ventaRepositoryJpa;
    private VentaRepository ventaRepository;

    public List<Venta> getAllVentas() {
        return ventaRepositoryJpa.findAll();
    }

    public Venta getVentaById(Long id) {
        return ventaRepositoryJpa.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
    }

    public Venta saveVenta(Venta venta) {
        return ventaRepositoryJpa.save(venta);
    }

    public void deleteVenta(Long id) {
        ventaRepositoryJpa.deleteById(id);
    }

    public List<Venta> getVentasByUsuarioId(Venta venta) {
        List<Venta> ventas = ventaRepository.findByUsuario(venta.getUsuario());
        if (ventas == null || ventas.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return ventas;
    }
}
