package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.Carrito;
import com.example.PerfulandiaSpa.model.ItemCarrito;
import com.example.PerfulandiaSpa.model.Usuario;
import com.example.PerfulandiaSpa.services.CarritoService;
import com.example.PerfulandiaSpa.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carrito")
@Tag    (name = "Carrito", description = "Controlador de Carrito de Compras")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;
    @Autowired
    private UsuarioService usuarioService;

@PostMapping("/{usuarioId}/agregar")
@Operation(
    summary = "Agregar item al carrito",
    description = "Agrega un item al carrito del usuario especificado.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Item agregado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    }
)
public ResponseEntity<Carrito> agregarItem(@PathVariable Long usuarioId, @RequestBody ItemCarrito item) {
    try {
        Usuario usuario = usuarioService.getUsuarioById(usuarioId);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        Carrito carrito = carritoService.obtenerCarrito(usuarioId).orElse(new Carrito());
        carrito.setUsuario(usuario);
        item.setCarrito(carrito); // Asegura la relación bidireccional
        Carrito updatedCarrito = carritoService.agregarItem(carrito, item, usuario);
        return ResponseEntity.ok(updatedCarrito);
    } catch (Exception e) {
        return ResponseEntity.status(500).build();
    }
}

    @GetMapping("/{usuarioId}")
    @Operation(summary = "Obtener carrito por usuario", description = "Devuelve el carrito de compras del usuario especificado.",responses = {@ApiResponse(responseCode = "200", description = "Carrito obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "204", description = "No hay items en el carrito")})
    public ResponseEntity<Carrito> obtenerCarrito(@PathVariable Long usuarioId) {
        return carritoService.obtenerCarrito(usuarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{usuarioId}/vaciar")
    @Operation(summary = "Vaciar carrito", description = "Elimina todos los items del carrito del usuario especificado.",responses = {@ApiResponse(responseCode = "204", description = "Carrito vaciado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "Solicitud incorrecta")})
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long usuarioId) {
        carritoService.vaciarCarrito(usuarioId);
        return ResponseEntity.noContent().build();
    }
}