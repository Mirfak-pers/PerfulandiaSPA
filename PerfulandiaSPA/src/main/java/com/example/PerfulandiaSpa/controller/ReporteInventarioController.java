package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.ReporteInventario;
import com.example.PerfulandiaSpa.services.ReporteInventarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reporte-inventario")
@Tag(name = "ReporteInventario", description = "Controlador de Reporte de Inventario")
public class ReporteInventarioController {

    @Autowired
    private ReporteInventarioService reporteInventarioService;

    @PostMapping
    @Operation(summary = "Crear nuevo reporte de inventario", description = "Registra un nuevo reporte de inventario en el sistema.",responses = {@ApiResponse(responseCode = "200", description = "Reporte de inventario creado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),@ApiResponse(responseCode = "409", description = "Conflicto al crear el reporte de inventario")})
    public ResponseEntity<ReporteInventario> createReporteInventario(@RequestBody ReporteInventario inventario) {
        try {
            ReporteInventario reporte = reporteInventarioService.saveReporteInventario(inventario);
            return ResponseEntity.status(HttpStatus.CREATED).body(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los reportes de inventario", description = "Devuelve una lista de todos los reportes de inventario registrados.",responses = {@ApiResponse(responseCode = "200", description = "Lista de reportes de inventario obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No hay reportes de inventario registrados"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "Solicitud incorrecta")})
    public ResponseEntity<List<ReporteInventario>> getAllReportesInventario() {
        List<ReporteInventario> reportes = reporteInventarioService.getAllReportesInventario();
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }
}