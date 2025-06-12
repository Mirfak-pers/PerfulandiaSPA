package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.Envio;
import com.example.PerfulandiaSpa.model.EnvioDetalle;
import com.example.PerfulandiaSpa.model.EstadoEnvio;
import com.example.PerfulandiaSpa.model.Sucursal;
import com.example.PerfulandiaSpa.services.EnvioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;





class EnvioControllerTest {

    @InjectMocks
    private EnvioController envioController;

    @Mock
    private EnvioService envioService;

    private Envio envio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        envio = new Envio();
        EstadoEnvio ee = new EstadoEnvio();
        ee.setId(1L);
        ee.setNombre("Enviado");
        ee.setDescripcion("PRoducto despachado");
        Sucursal destino = new Sucursal();
        Sucursal origen = new Sucursal();
        List<EnvioDetalle> detalles = new ArrayList<>(); 
        envio.setId(1L);
        envio.setEstado(ee);
        envio.setDestino(destino);
        envio.setOrigen(origen);
        envio.setDetalles(detalles);
        envio.setFechaEnvio(LocalDate.now().atStartOfDay());
        envio.setFechaRecepcion(LocalDate.now().plusDays(2).atStartOfDay());
    }

    @Test
    void getAllEnvios_returnsList_whenEnviosExist() {
        when(envioService.getAllEnvios()).thenReturn(Arrays.asList(envio));
        ResponseEntity<List<Envio>> response = envioController.getAllEnvios();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getAllEnvios_returnsNoContent_whenNoEnvios() {
        when(envioService.getAllEnvios()).thenReturn(Collections.emptyList());
        ResponseEntity<List<Envio>> response = envioController.getAllEnvios();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getEnvioById_returnsEnvio_whenFound() {
        when(envioService.getEnvioById(1)).thenReturn(envio);
        ResponseEntity<Envio> response = envioController.getEnvioById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(envio, response.getBody());
    }

    @Test
    void getEnvioById_returnsNotFound_whenNotFound() {
        when(envioService.getEnvioById(2)).thenThrow(new RuntimeException("Not found"));
        ResponseEntity<Envio> response = envioController.getEnvioById(2);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void createEnvio_returnsCreatedEnvio() {
        when(envioService.saveEnvio(envio)).thenReturn(envio);
        ResponseEntity<Envio> response = envioController.createEnvio(envio);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(envio, response.getBody());
    }

    @Test
    void updateEnvio_returnsUpdatedEnvio_whenFound() {
                EstadoEnvio nuevoEstado = new EstadoEnvio(2L, "Recibido", "entregado");
                nuevoEstado.setId(2L);
                nuevoEstado.setNombre(nuevoEstado.getNombre());
                nuevoEstado.setDescripcion("Producto recibido");

                Sucursal nuevoDestino = new Sucursal();
                nuevoDestino.setId(2L);
                nuevoDestino.setNombre("Nuevo Destino");

                Sucursal nuevoOrigen = new Sucursal();
                nuevoOrigen.setId(3L);
                nuevoOrigen.setNombre("Nuevo Origen");

                List<EnvioDetalle> nuevosDetalles = new ArrayList<>();
                EnvioDetalle detalle = new EnvioDetalle();
                detalle.setId(1L);
                nuevosDetalles.add(detalle);

                Envio updatedEnvio = new Envio();
                updatedEnvio.setId(1L);
                updatedEnvio.setEstado(nuevoEstado);
                updatedEnvio.setDestino(nuevoDestino);
                updatedEnvio.setOrigen(nuevoOrigen);
                updatedEnvio.setDetalles(nuevosDetalles);
                updatedEnvio.setFechaEnvio(LocalDate.now().plusDays(1).atStartOfDay());
                updatedEnvio.setFechaRecepcion(LocalDate.now().plusDays(3).atStartOfDay());

        when(envioService.getEnvioById(1)).thenReturn(envio);
        when(envioService.saveEnvio(any(Envio.class))).thenReturn(updatedEnvio);

        ResponseEntity<Envio> response = envioController.updateEnvio(1, updatedEnvio);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(nuevoEstado.getNombre(), response.getBody().getEstado().getNombre());
        assertEquals("Nuevo Destino", response.getBody().getDestino().getNombre());
    }

    @Test
    void updateEnvio_returnsNotFound_whenEnvioDoesNotExist() {
        when(envioService.getEnvioById(2)).thenThrow(new RuntimeException("Not found"));
        ResponseEntity<Envio> response = envioController.updateEnvio(2, envio);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteEnvio_returnsOk_whenDeleted() {
        doNothing().when(envioService).deleteEnvio(1);
        ResponseEntity<String> response = envioController.deleteEnvio(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Envio eliminado exitosamente.", response.getBody());
    }

    @Test
    void deleteEnvio_returnsNotFound_whenEnvioDoesNotExist() {
        doThrow(new RuntimeException("Not found")).when(envioService).deleteEnvio(2);
        ResponseEntity<String> response = envioController.deleteEnvio(2);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("Envio no encontrado con ID: 2"));
    }
}