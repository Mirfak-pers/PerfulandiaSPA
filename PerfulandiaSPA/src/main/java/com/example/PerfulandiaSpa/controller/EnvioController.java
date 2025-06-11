package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.Envio;
import com.example.PerfulandiaSpa.services.EnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/envios")
@Tag(name = "Envio", description = "Controlador de Envio")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    @Operation(summary = "Obtener todos los envíos", description = "Devuelve una lista de todos los envíos registrados.",responses = {@ApiResponse(responseCode = "200", description = "Lista de envíos obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No hay envíos registrados"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "204", description = "No hay envíos disponibles")})
    public ResponseEntity<List<Envio>> getAllEnvios() {
        List<Envio> envios = envioService.getAllEnvios();
        if (envios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(envios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener envío por ID", description = "Devuelve los detalles de un envío específico por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Envio encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Envio no encontrado"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de envío inválido")})
    public ResponseEntity<Envio> getEnvioById(@PathVariable int id) {
        try {
            Envio envio = envioService.getEnvioById(id);
            return ResponseEntity.ok(envio);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    @Operation(summary = "Crear nuevo envío", description = "Registra un nuevo envío en el sistema.",responses = {@ApiResponse(responseCode = "201", description = "Envio creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),@ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    public ResponseEntity<Envio> createEnvio(@RequestBody Envio envio) {
        Envio saved = envioService.saveEnvio(envio);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar envío", description = "Actualiza los detalles de un envío existente por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Envio actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Envio no encontrado"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de envío inválido")})
    public ResponseEntity<Envio> updateEnvio(@PathVariable int id, @RequestBody Envio envio) {
        try {
            Envio existing = envioService.getEnvioById(id);
            existing.setEstado(envio.getEstado());
            existing.setDestino(envio.getDestino());
            existing.setOrigen(envio.getOrigen());
            existing.setDetalles(envio.getDetalles());
            existing.setFechaEnvio(envio.getFechaEnvio());
            existing.setFechaRecepcion(envio.getFechaRecepcion());
            Envio updated = envioService.saveEnvio(existing);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar envío", description = "Elimina un envío existente por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Envio eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Envio no encontrado"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de envío inválido")})
    public ResponseEntity<String> deleteEnvio(@PathVariable int id) {
        try {
            envioService.deleteEnvio(id);
            return ResponseEntity.ok("Envio eliminado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Envio no encontrado con ID: " + id);
        }
    }
}