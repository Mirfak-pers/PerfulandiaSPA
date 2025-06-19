package com.example.PerfulandiaSpa.controller;


import com.example.PerfulandiaSpa.model.Venta;
import com.example.PerfulandiaSpa.services.VentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;




class VentaControllerTest {

    @InjectMocks
    private VentaController ventaController;

    @Mock
    private VentaService ventaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllVentas_returnsVentasList_whenVentasExist() {
        Venta venta = new Venta();
        when(ventaService.getAllVentas()).thenReturn(Arrays.asList(venta));

        ResponseEntity<List<Venta>> response = ventaController.getAllVentas();

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void getAllVentas_returnsNoContent_whenNoVentasExist() {
        when(ventaService.getAllVentas()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Venta>> response = ventaController.getAllVentas();

        assertEquals(204, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getVentaById_returnsVenta_whenFound() {
        Venta venta = new Venta();
        when(ventaService.getVentaById(1L)).thenReturn(venta);

        ResponseEntity<Venta> response = ventaController.getVentaById(1L);

        assertEquals(200, response.getStatusCode());
        assertEquals(venta, response.getBody());
    }

    @Test
    void getVentaById_returnsNotFound_whenNotFound() {
        when(ventaService.getVentaById(1L)).thenThrow(new RuntimeException());

        ResponseEntity<Venta> response = ventaController.getVentaById(1L);

        assertEquals(404, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void createVenta_returnsCreatedVenta() {
        Venta venta = new Venta();
        Venta savedVenta = new Venta();
        when(ventaService.saveVenta(venta)).thenReturn(savedVenta);

        ResponseEntity<Venta> response = ventaController.createVenta(venta);

        assertEquals(201, response.getStatusCode());
        assertEquals(savedVenta, response.getBody());
    }

    @Test
    void deleteVenta_returnsOk_whenVentaDeleted() {
        doNothing().when(ventaService).deleteVenta(1L);

        ResponseEntity<String> response = ventaController.deleteVenta(1L);

        assertEquals(200, response.getStatusCode());
        assertEquals("Venta eliminada exitosamente.", response.getBody());
    }

    @Test
    void deleteVenta_returnsNotFound_whenVentaNotFound() {
        doThrow(new RuntimeException()).when(ventaService).deleteVenta(1L);

        ResponseEntity<String> response = ventaController.deleteVenta(1L);

        assertEquals(404, response.getStatusCode());
        assertTrue(response.getBody().contains("Venta no encontrada"));
    }

    @Test
    void getVentasByUsuario_returnsVentasList_whenVentasExist() {
        Venta venta = new Venta();
        List<Venta> ventas = Arrays.asList(venta);
        when(ventaService.getVentasByUsuarioId(venta)).thenReturn(ventas);

        ResponseEntity<List<Venta>> response = ventaController.getVentasByUsuario(venta);

        assertEquals(200, response.getStatusCode());
        assertEquals(ventas, response.getBody());
    }

    @Test
    void getVentasByUsuario_returnsNoContent_whenNoVentasExist() {
        Venta venta = new Venta();
        when(ventaService.getVentasByUsuarioId(venta)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Venta>> response = ventaController.getVentasByUsuario(venta);

        assertEquals(204, response.getStatusCode());
        assertNull(response.getBody());
    }
}

