package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.Sucursal;
import com.example.PerfulandiaSpa.services.SucursalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sucursales")
@Tag(name="Scursal",description = "COntrolador de Sucursal")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    @Operation(summary = "Obtener todas las sucursales", description = "Devuelve una lista de todas las sucursales registradas.",responses = {@ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay sucursales registradas"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "Solicitud incorrecta")})
    public ResponseEntity<List<Sucursal>> getAllSucursales() {
        List<Sucursal> sucursales = sucursalService.getAllSucursales();
        if (sucursales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sucursales);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sucursal por ID", description = "Devuelve los detalles de una sucursal específica por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Sucursal encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de sucursal inválido")})
    public ResponseEntity<Sucursal> getSucursalById(@PathVariable Long id) {
        try {
            Sucursal sucursal = sucursalService.getSucursalById(id);
            return ResponseEntity.ok(sucursal);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    @Operation(summary = "Crear nueva sucursal", description = "Registra una nueva sucursal en el sistema.",responses = {@ApiResponse(responseCode = "201", description = "Sucursal creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "409", description = "Conflicto al crear la sucursal")})
    public ResponseEntity<Sucursal> createSucursal(@RequestBody Sucursal sucursal) {
        Sucursal saved = sucursalService.saveSucursal(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sucursal", description = "Actualiza los detalles de una sucursal existente por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de sucursal inválido")})
    public ResponseEntity<Sucursal> updateSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        try {
            Sucursal existing = sucursalService.getSucursalById(id);
            existing.setNombre(sucursal.getNombre());
            existing.setDireccion(sucursal.getDireccion());
            Sucursal updated = sucursalService.saveSucursal(existing);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sucursal", description = "Elimina una sucursal del sistema por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Sucursal eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de sucursal inválido")})
    public ResponseEntity<String> deleteSucursal(@PathVariable Long id) {
        try {
            sucursalService.deleteSucursal(id);
            return ResponseEntity.ok("Sucursal eliminada exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sucursal no encontrada con ID: " + id);
        }
    }
}