package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.ReporteVenta;
import com.example.PerfulandiaSpa.services.ReporteVentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reporte-ventas")
@Tag(name = "ReporteVentas", description = "Controlador de Reporte de Ventas")
public class ReporteVentasController {

    @Autowired
    private ReporteVentaService reporteVentaService;

    @PostMapping
    @Operation(summary = "Crear nuevo reporte de venta", description = "Registra un nuevo reporte de venta en el sistema.",responses = {@ApiResponse(responseCode = "200", description = "Reporte de venta creado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),@ApiResponse(responseCode = "409", description = "Conflicto al crear el reporte de venta")})
    public ResponseEntity<ReporteVenta> createReporteVenta(@RequestBody ReporteVenta venta) {
        try {
            ReporteVenta reporte = reporteVentaService.saveReporteVenta(venta);
            return ResponseEntity.status(HttpStatus.CREATED).body(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los reportes de venta", description = "Devuelve una lista de todos los reportes de venta registrados.",responses = {@ApiResponse(responseCode = "200", description = "Lista de reportes de venta obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No hay reportes de venta registrados"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "204", description = "No hay reportes de venta disponibles")})
    public ResponseEntity<List<ReporteVenta>> getAllReportesVenta() {
        List<ReporteVenta> reportes = reporteVentaService.getAllReportesVenta();
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }
}