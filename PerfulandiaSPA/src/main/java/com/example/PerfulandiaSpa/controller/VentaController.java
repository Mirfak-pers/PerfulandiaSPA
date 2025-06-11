package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.Venta;
import com.example.PerfulandiaSpa.services.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ventas")
@Tag(name = "Venta", description = "Controlador de Venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    @Operation(summary = "Obtener todas las ventas", description = "Devuelve una lista de todas las ventas registradas.",responses = {@ApiResponse(responseCode = "200", description = "Lista de ventas obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay ventas registradas"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "Solicitud incorrecta")})
    public ResponseEntity<List<Venta>> getAllVentas() {
        List<Venta> ventas = ventaService.getAllVentas();
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }

    

    @GetMapping("/{id}")
    @Operation(summary = "Obtener venta por ID", description = "Devuelve los detalles de una venta específica por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Venta encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de venta inválido")})
    public ResponseEntity<Venta> getVentaById(@PathVariable Long id) {
        try {
            Venta venta = ventaService.getVentaById(id);
            return ResponseEntity.ok(venta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear nueva venta", description = "Registra una nueva venta en el sistema.",responses = {@ApiResponse(responseCode = "201", description = "Venta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "409", description = "Conflicto al crear la venta"),@ApiResponse(responseCode = "204", description = "No se pudo crear la venta")})
    public ResponseEntity<Venta> createVenta(@RequestBody Venta venta) {
        Venta saved = ventaService.saveVenta(venta);
        return ResponseEntity.status(201).body(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar venta", description = "Elimina una venta existente por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Venta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de venta inválido")})
    public ResponseEntity<String> deleteVenta(@PathVariable Long id) {
        try {
            ventaService.deleteVenta(id);
            return ResponseEntity.ok("Venta eliminada exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Venta no encontrada con ID: " + id);
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener ventas por usuario", description = "Devuelve una lista de ventas asociadas a un usuario específico.",responses = {@ApiResponse(responseCode = "200", description = "Ventas obtenidas exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay ventas registradas para este usuario"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de usuario inválido")})
    public ResponseEntity<List<Venta>> getVentasByUsuario(@PathVariable Venta venta) {
        List<Venta> ventas = ventaService.getVentasByUsuarioId(venta);
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }
}