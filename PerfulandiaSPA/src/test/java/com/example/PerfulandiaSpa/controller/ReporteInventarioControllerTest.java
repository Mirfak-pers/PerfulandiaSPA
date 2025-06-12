package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.ReporteInventario;
import com.example.PerfulandiaSpa.services.ReporteInventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;





class ReporteInventarioControllerTest {

    private ReporteInventarioService reporteInventarioService;
    private ReporteInventarioController controller;

    @BeforeEach
    void setUp() {
        reporteInventarioService = mock(ReporteInventarioService.class);
        controller = new ReporteInventarioController();
        // Inject mock service
        java.lang.reflect.Field field;
        try {
            field = ReporteInventarioController.class.getDeclaredField("reporteInventarioService");
            field.setAccessible(true);
            field.set(controller, reporteInventarioService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createReporteInventario_ReturnsCreated() {
        ReporteInventario input = new ReporteInventario();
        ReporteInventario saved = new ReporteInventario();
        when(reporteInventarioService.saveReporteInventario(input)).thenReturn(saved);

        ResponseEntity<ReporteInventario> response = controller.createReporteInventario(input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(saved, response.getBody());
        verify(reporteInventarioService).saveReporteInventario(input);
    }

    @Test
    void createReporteInventario_ReturnsInternalServerErrorOnException() {
        ReporteInventario input = new ReporteInventario();
        when(reporteInventarioService.saveReporteInventario(input)).thenThrow(new RuntimeException("DB error"));

        ResponseEntity<ReporteInventario> response = controller.createReporteInventario(input);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(reporteInventarioService).saveReporteInventario(input);
    }

    @Test
    void getAllReportesInventario_ReturnsOkWithList() {
        List<ReporteInventario> reportes = Arrays.asList(new ReporteInventario(), new ReporteInventario());
        when(reporteInventarioService.getAllReportesInventario()).thenReturn(reportes);

        ResponseEntity<List<ReporteInventario>> response = controller.getAllReportesInventario();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reportes, response.getBody());
        verify(reporteInventarioService).getAllReportesInventario();
    }

    @Test
    void getAllReportesInventario_ReturnsNoContentWhenEmpty() {
        when(reporteInventarioService.getAllReportesInventario()).thenReturn(Collections.emptyList());

        ResponseEntity<List<ReporteInventario>> response = controller.getAllReportesInventario();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(reporteInventarioService).getAllReportesInventario();
    }
}