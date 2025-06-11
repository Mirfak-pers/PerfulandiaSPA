package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.Producto;
import com.example.PerfulandiaSpa.model.Provedor;
import com.example.PerfulandiaSpa.services.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Producto", description = "Controlador de Productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation (summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos registrados.",responses = {@ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No hay productos registrados"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "204", description = "No hay productos disponibles")})  
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productoService.getAllProductos();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Devuelve los detalles de un producto específico por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),@ApiResponse(responseCode = "400", description = "ID de producto inválido"),@ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    public ResponseEntity<Producto> getProductoById(@PathVariable int id) {
        try {
            Producto producto = productoService.getProductoById(id);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    @Operation(summary = "Crear nuevo producto", description = "Registra un nuevo producto en el sistema.",responses = {@ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "409", description = "Conflicto al crear el producto")})
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Producto saved = productoService.saveProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza los detalles de un producto existente por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),@ApiResponse(responseCode = "400", description = "ID de producto inválido"),@ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    public ResponseEntity<Producto> updateProducto(@PathVariable int id, @RequestBody Producto producto) {
        try {
            Producto existing = productoService.getProductoById(id);
            existing.setNombre(producto.getNombre());
            existing.setCategoria(producto.getCategoria());
            existing.setEstado(producto.getEstado());
            existing.setPrecio(producto.getPrecio());
            existing.setStock_total(producto.getStock_total());
            Producto updated = productoService.saveProducto(existing);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del sistema por su ID.",responses = {@ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),@ApiResponse(responseCode = "500", description = "Error interno del servidor"),@ApiResponse(responseCode = "400", description = "ID de producto inválido")})
    public ResponseEntity<String> deleteProducto(@PathVariable int id) {
        try {
            productoService.deleteProducto(id);
            return ResponseEntity.ok("Producto eliminado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con ID: " + id);
        }
    }

    @GetMapping("/proveedor/{proveedorId}")
    @Operation(summary = "Obtener productos por proveedor", description = "Devuelve una lista de productos asociados a un proveedor específico.",responses = {@ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No hay productos registrados para el proveedor"),@ApiResponse(responseCode = "400", description = "ID de proveedor inválido"),@ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    public ResponseEntity<List<Producto>> getProductosByProveedor(@PathVariable int proveedorId) {
        Provedor provedor = new Provedor();
        provedor.setId(proveedorId);
        List<Producto> productos = productoService.getProductosByProveedor(provedor);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
}