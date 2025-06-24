// filepath: [ReporteRendimientoService.java](http://_vscodecontentref_/4)
package com.example.PerfulandiaSpa.services;

import com.example.PerfulandiaSpa.model.ReporteRendimiento;
import com.example.PerfulandiaSpa.model.Sucursal;
import com.example.PerfulandiaSpa.repository.ReporteRendimientoRepository;
import com.example.PerfulandiaSpa.repository.ReporteRendimientoRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteRendimientoService {

    @Autowired
    private ReporteRendimientoRepositoryJpa reporteRendimientoRepositoryJpa;
    private ReporteRendimientoRepository reporteRendimientoRepository;

    public ReporteRendimiento saveReporteRendimiento(ReporteRendimiento rendimiento) {
        return reporteRendimientoRepositoryJpa.save(rendimiento);
    }

    public List<ReporteRendimiento> getAllReportesRendimiento() {
        return reporteRendimientoRepositoryJpa.findAll();
    }

    public void deleteReporteRendimiento(Long id) {
        reporteRendimientoRepositoryJpa.deleteById(id);
    }

    public List<ReporteRendimiento> getReportesBySucursal(Sucursal sucursal) {
    return reporteRendimientoRepository.findBySucursal(sucursal);
    }

        public List<ReporteRendimiento> getReportesById(Long id) {
        return reporteRendimientoRepository.buscarPorId(id)
                .map(List::of)
                .orElseGet(List::of);
    }
}