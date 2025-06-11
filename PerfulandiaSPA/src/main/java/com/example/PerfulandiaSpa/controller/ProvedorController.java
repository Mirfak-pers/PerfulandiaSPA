package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.Provedor;
import com.example.PerfulandiaSpa.services.ProvedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/provedores")
@Tag(name = "Provedor", description = "Controlador de Provedor")
public class ProvedorController {

    @Autowired
    private ProvedorService provedorService;

    @GetMapping
    @Operation(summary = "Obtener todos los provedores", description = "Devuelve una lista de todos los provedores registrados.",responses = {@ApiResponse(responseCode = "200", description = "Lista de provedores obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay provedores registrados"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "404", description = "No se encontraron provedores")})
    public ResponseEntity<List<Provedor>> getAllProvedores() {
        List<Provedor> provedores = provedorService.getAllProvedores();
        if (provedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(provedores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener provedor por ID", description = "Devuelve los detalles de un provedor específico por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Provedor encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Provedor no encontrado"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de provedor inválido")})
    public ResponseEntity<Provedor> getProvedorById(@PathVariable int id) {
        try {
            Provedor provedor = provedorService.getProvedorById(id);
            return ResponseEntity.ok(provedor);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    @Operation(summary = "Crear nuevo provedor", description = "Registra un nuevo provedor en el sistema.",responses = {@ApiResponse(responseCode = "201", description = "Provedor creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "409", description = "Conflicto al crear el provedor")})
    public ResponseEntity<Provedor> createProvedor(@RequestBody Provedor provedor) {
        Provedor saved = provedorService.saveProvedor(provedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar provedor", description = "Actualiza los detalles de un provedor existente por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Provedor actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Provedor no encontrado"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de provedor inválido")})
    public ResponseEntity<Provedor> updateProvedor(@PathVariable int id, @RequestBody Provedor provedor) {
        try {
            Provedor existing = provedorService.getProvedorById(id);
            existing.setNombre(provedor.getNombre());
            existing.setDireccion(provedor.getDireccion());
            existing.setEmail(provedor.getEmail());
            existing.setTelefono(provedor.getTelefono());
            existing.setContacto(provedor.getContacto());
            Provedor updated = provedorService.saveProvedor(existing);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar provedor", description = "Elimina un provedor del sistema por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Provedor eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Provedor no encontrado"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de provedor inválido")})
    public ResponseEntity<String> deleteProvedor(@PathVariable int id) {
        try {
            provedorService.deleteProvedor(id);
            return ResponseEntity.ok("Provedor eliminado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provedor no encontrado con ID: " + id);
        }
    }
}