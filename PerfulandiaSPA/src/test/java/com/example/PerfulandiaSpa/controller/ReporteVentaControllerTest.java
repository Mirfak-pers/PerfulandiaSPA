package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.ReporteVenta;
import com.example.PerfulandiaSpa.model.Sucursal;
import com.example.PerfulandiaSpa.services.ReporteVentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;





public class ReporteVentaControllerTest {

    @InjectMocks
    private ReporteVentasController reporteVentaController;

    @Mock
    private ReporteVentaService reporteVentaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetReporteVentas() {
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        List<ReporteVenta> reportes = Arrays.asList(new ReporteVenta(), new ReporteVenta());
        when(reporteVentaService.getReporteVentas(sucursal)).thenReturn(reportes);

        ResponseEntity<List<ReporteVenta>> response = reporteVentaController.getReporteVentas(sucursal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reportes, response.getBody());
        verify(reporteVentaService, times(1)).getReporteVentas(sucursal);
    }

    @Test
    public void testGetReporteVentasEmpty() {
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        when(reporteVentaService.getReporteVentas(sucursal)).thenReturn(Collections.emptyList());

        ResponseEntity<List<ReporteVenta>> response = reporteVentaController.getReporteVentas(sucursal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(reporteVentaService, times(1)).getReporteVentas(sucursal);
    }
}