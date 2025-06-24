package com.example.PerfulandiaSpa.controller;


import com.example.PerfulandiaSpa.model.EnvioDetalle;
import com.example.PerfulandiaSpa.services.EnvioDetalleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;




class EnvioDetalleControllerTest {

    @Mock
    private EnvioDetalleService envioDetalleService;

    @InjectMocks
    private EnvioDetalleController envioDetalleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEnvioDetalles() {
        EnvioDetalle detalle1 = new EnvioDetalle();
        EnvioDetalle detalle2 = new EnvioDetalle();
        List<EnvioDetalle> detalles = Arrays.asList(detalle1, detalle2);

        when(envioDetalleService.getAllEnvioDetalles()).thenReturn(detalles);

        ResponseEntity<List<EnvioDetalle>> response = envioDetalleController.getAllEnvioDetalles();

        assertEquals(200, response.getStatusCode());
        assertEquals(detalles, response.getBody());
        verify(envioDetalleService, times(1)).getAllEnvioDetalles();
    }

    @Test
    void testCreateEnvioDetalle() {
        EnvioDetalle inputDetalle = new EnvioDetalle();
        EnvioDetalle savedDetalle = new EnvioDetalle();
        savedDetalle.setId(1L);

        when(envioDetalleService.saveEnvioDetalle(inputDetalle)).thenReturn(savedDetalle);

        ResponseEntity<EnvioDetalle> response = envioDetalleController.createEnvioDetalle(inputDetalle);

        assertEquals(200, response.getStatusCode());
        assertEquals(savedDetalle, response.getBody());
        verify(envioDetalleService, times(1)).saveEnvioDetalle(inputDetalle);
    }

    @Test
    void testDeleteEnvioDetalle() {
        Long id = 1L;

        doNothing().when(envioDetalleService).deleteEnvioDetalle(id);

        ResponseEntity<Void> response = envioDetalleController.deleteEnvioDetalle(id);

        assertEquals(204, response.getStatusCode());
        assertNull(response.getBody());
        verify(envioDetalleService, times(1)).deleteEnvioDetalle(id);
    }
}
