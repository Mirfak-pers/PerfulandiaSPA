package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.Producto;
import com.example.PerfulandiaSpa.model.Provedor;
import com.example.PerfulandiaSpa.services.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;





class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProductos_returnsList() {
        Producto producto = new Producto();
        when(productoService.getAllProductos()).thenReturn(Arrays.asList(producto));

        ResponseEntity<List<Producto>> response = productoController.getAllProductos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void getAllProductos_returnsNoContent() {
        when(productoService.getAllProductos()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Producto>> response = productoController.getAllProductos();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getProductoById_found() {
        Producto producto = new Producto();
        when(productoService.getProductoById(1)).thenReturn(producto);

        ResponseEntity<Producto> response = productoController.getProductoById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(producto, response.getBody());
    }

    @Test
    void getProductoById_notFound() {
        when(productoService.getProductoById(1)).thenThrow(new RuntimeException());

        ResponseEntity<Producto> response = productoController.getProductoById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void createProducto_success() {
        Producto producto = new Producto();
        when(productoService.saveProducto(producto)).thenReturn(producto);

        ResponseEntity<Producto> response = productoController.createProducto(producto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(producto, response.getBody());
    }

    @Test
    void updateProducto_success() {
        Producto producto = new Producto();
        producto.setNombre("Nuevo");
        producto.setCategoria("Cat");
        producto.setEstado("Activo");
        producto.setPrecio(1000);
        producto.setStock_total(10);

        Producto existing = new Producto();
        when(productoService.getProductoById(1)).thenReturn(existing);
        when(productoService.saveProducto(existing)).thenReturn(existing);

        ResponseEntity<Producto> response = productoController.updateProducto(1, producto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existing, response.getBody());
        verify(productoService).saveProducto(existing);
    }

    @Test
    void updateProducto_notFound() {
        Producto producto = new Producto();
        when(productoService.getProductoById(1)).thenThrow(new RuntimeException());

        ResponseEntity<Producto> response = productoController.updateProducto(1, producto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteProducto_success() {
        doNothing().when(productoService).deleteProducto(1);

        ResponseEntity<String> response = productoController.deleteProducto(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Producto eliminado exitosamente.", response.getBody());
    }

    @Test
    void deleteProducto_notFound() {
        doThrow(new RuntimeException()).when(productoService).deleteProducto(1);

        ResponseEntity<String> response = productoController.deleteProducto(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("Producto no encontrado"));
    }

    @Test
    void getProductosByProveedor_returnsList() {
        Provedor provedor = new Provedor();
        provedor.setId(1);
        Producto producto = new Producto();
        when(productoService.getProductosByProveedor(ArgumentMatchers.any(Provedor.class)))
                .thenReturn(Arrays.asList(producto));

        ResponseEntity<List<Producto>> response = productoController.getProductosByProveedor(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void getProductosByProveedor_returnsNoContent() {
        when(productoService.getProductosByProveedor(ArgumentMatchers.any(Provedor.class)))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<Producto>> response = productoController.getProductosByProveedor(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}