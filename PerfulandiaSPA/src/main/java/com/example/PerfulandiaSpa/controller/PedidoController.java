package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.Pedido;
import com.example.PerfulandiaSpa.services.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedido", description = "Controlador de Pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Obtener todos los pedidos", description = "Devuelve una lista de todos los pedidos registrados.",responses = {@ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No hay pedidos registrados"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "204", description = "No hay pedidos disponibles")})  
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        List<Pedido> pedidos = pedidoService.getAllPedidos();
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID", description = "Devuelve los detalles de un pedido específico por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Pedido encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de pedido inválido")})
    public ResponseEntity<Pedido> getPedidoById(@PathVariable int id) {
        try {
            Pedido pedido = pedidoService.getPedidoById(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener pedidos por usuario", description = "Devuelve una lista de pedidos asociados a un usuario específico.",responses = {@ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No hay pedidos registrados para el usuario"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de usuario inválido")})
    public ResponseEntity<List<Pedido>> getPedidosByUsuario(@PathVariable Pedido pedido) {
        List<Pedido> pedidos = pedidoService.getPedidosByUsuario(pedido.getUsuario());
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    // Buscar pedidos por sucursal
    @GetMapping("/sucursal/{sucursalId}")
    @Operation(summary = "Obtener pedidos por sucursal", description = "Devuelve una lista de pedidos asociados a una sucursal específica.",responses = {@ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No hay pedidos registrados para la sucursal"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de sucursal inválido")})
    // Cambié el parámetro de ruta para que sea un ID de sucursal
    public ResponseEntity<List<Pedido>> getPedidosBySucursal(@PathVariable Pedido pedido) {
        List<Pedido> pedidos = pedidoService.getPedidosBySucursal(pedido.getSucursal());
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo pedido", description = "Registra un nuevo pedido en el sistema.",responses = {@ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "409", description = "Conflicto al crear el pedido")})
    public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido) {
        Pedido saved = pedidoService.savePedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pedido", description = "Actualiza los detalles de un pedido existente por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de pedido inválido")})
    public ResponseEntity<Pedido> updatePedido(@PathVariable int id, @RequestBody Pedido pedido) {
        try {
            Pedido existing = pedidoService.getPedidoById(id);
            existing.setEstado(pedido.getEstado());
            existing.setFechaCreacion(pedido.getFechaCreacion());
            existing.setProductos(pedido.getProductos());
            Pedido updated = pedidoService.savePedido(existing);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido del sistema por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Pedido eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de pedido inválido")})
    public ResponseEntity<String> deletePedido(@PathVariable int id) {
        try {
            pedidoService.deletePedido(id);
            return ResponseEntity.ok("Pedido eliminado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado con ID: " + id);
        }
    }
}