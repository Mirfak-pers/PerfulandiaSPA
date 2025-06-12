package com.example.PerfulandiaSpa.controller;


import com.example.PerfulandiaSpa.model.EnvioDetalle;
import com.example.PerfulandiaSpa.services.EnvioDetalleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;




@RestController
@RequestMapping("/api/envio-detalles")
@Tag(name = "EnvioDetalle", description = "Operaciones relacionadas con los detalles de envío")
public class EnvioDetalleController {

    @Autowired
    private EnvioDetalleService envioDetalleService;

    @Operation(summary = "Obtener todos los detalles de envío")
    @GetMapping
    public ResponseEntity<List<EnvioDetalle>> getAllEnvioDetalles() {
        return ResponseEntity.ok(envioDetalleService.getAllEnvioDetalles());
    }



    @Operation(summary = "Crear un nuevo detalle de envío")
    @PostMapping
    public ResponseEntity<EnvioDetalle> createEnvioDetalle(@RequestBody EnvioDetalle envioDetalle) {
        EnvioDetalle created = envioDetalleService.saveEnvioDetalle(envioDetalle);
        return ResponseEntity.ok(created);
    }


    @Operation(summary = "Eliminar un detalle de envío por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvioDetalle(@PathVariable Long id) {
        envioDetalleService.deleteEnvioDetalle(id);
        return ResponseEntity.noContent().build();
    }
}