package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.Cupon;
import com.example.PerfulandiaSpa.services.CuponService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cupones")
@Tag(name = "Cupon", description = "Controlador de Cupones")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @PostMapping
    @Operation(summary = "Crear nuevo cupón", description = "Registra un nuevo cupón en el sistema.",responses = {@ApiResponse(responseCode = "200", description = "Cupón creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "409", description = "Conflicto al crear el cupón")})
    public ResponseEntity<Cupon> createCupon(@RequestBody Cupon cupon) {
        return ResponseEntity.ok(cuponService.saveCupon(cupon));
    }

    @GetMapping
    @Operation(summary = "Obtener todos los cupones", description = "Devuelve una lista de todos los cupones registrados.",responses = {@ApiResponse(responseCode = "200", description = "Lista de cupones obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No hay cupones registrados"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "204", description = "No hay cupones disponibles")})
    public ResponseEntity<List<Cupon>> getAllCupones() {
        return ResponseEntity.ok(cuponService.getAllCupones());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cupón por ID", description = "Devuelve los detalles de un cupón específico por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Cupón encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cupón no encontrado"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de cupón inválido")})
    public ResponseEntity<Cupon> getCuponById(@PathVariable Long id) {
        return cuponService.getCuponById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cupón", description = "Elimina un cupón específico por su ID.",responses = {@ApiResponse(responseCode = "204", description = "Cupón eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cupón no encontrado"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de cupón inválido")})
    public ResponseEntity<Void> deleteCupon(@PathVariable Long id) {
        cuponService.deleteCupon(id);
        return ResponseEntity.noContent().build();
    }
}