package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.ReporteRendimiento;
import com.example.PerfulandiaSpa.model.Sucursal;
import com.example.PerfulandiaSpa.services.ReporteRendimientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reporte-rendimiento")
@Tag(name = "ReporteRendimiento", description = "Controlador de Reporte de Rendimiento")
public class ReporteRendimientoController {

    @Autowired
    private ReporteRendimientoService reporteRendimientoService;

    @PostMapping
    @Operation  (summary = "Crear nuevo reporte de rendimiento", description = "Registra un nuevo reporte de rendimiento en el sistema.",responses = {@ApiResponse(responseCode = "200", description = "Reporte de rendimiento creado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    public ResponseEntity<ReporteRendimiento> createReporteRendimiento(@RequestBody ReporteRendimiento rendimiento) {
        try {
            ReporteRendimiento reporte = reporteRendimientoService.saveReporteRendimiento(rendimiento);
            return ResponseEntity.status(HttpStatus.CREATED).body(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los reportes de rendimiento", description = "Devuelve una lista de todos los reportes de rendimiento registrados.",responses = {@ApiResponse(responseCode = "200", description = "Lista de reportes de rendimiento obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No hay reportes de rendimiento registrados"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "204", description = "No hay reportes de rendimiento disponibles")})
    public ResponseEntity<List<ReporteRendimiento>> getAllReportesRendimiento() {
        List<ReporteRendimiento> reportes = reporteRendimientoService.getAllReportesRendimiento();
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }
    @GetMapping("/sucursal/{sucursalId}")
    @Operation(summary = "Obtener reportes de rendimiento por sucursal", description = "Devuelve una lista de reportes de rendimiento filtrados por sucursal.",responses = {@ApiResponse(responseCode = "200", description = "Lista de reportes de rendimiento obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay reportes de rendimiento para la sucursal especificada"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de sucursal inválido")})
    public ResponseEntity<List<ReporteRendimiento>> getReportesBySucursal(@PathVariable Long sucursalId) {
        Sucursal sucursal = new Sucursal();
        sucursal.setId(sucursalId);
        List<ReporteRendimiento> reportes = reporteRendimientoService.getReportesBySucursal(sucursal);
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }
}