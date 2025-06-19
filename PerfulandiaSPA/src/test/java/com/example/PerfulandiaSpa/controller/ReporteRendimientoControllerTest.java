package com.example.PerfulandiaSpa.controller;



import com.example.PerfulandiaSpa.model.ReporteRendimiento;
import com.example.PerfulandiaSpa.model.Sucursal;
import com.example.PerfulandiaSpa.services.ReporteRendimientoService;
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




class ReporteRendimientoControllerTest {

    @InjectMocks
    private ReporteRendimientoController controller;

    @Mock
    private ReporteRendimientoService reporteRendimientoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createReporteRendimiento_success() {
        ReporteRendimiento input = new ReporteRendimiento();
        ReporteRendimiento saved = new ReporteRendimiento();
        when(reporteRendimientoService.saveReporteRendimiento(input)).thenReturn(saved);

        ResponseEntity<ReporteRendimiento> response = controller.createReporteRendimiento(input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(saved, response.getBody());
        verify(reporteRendimientoService).saveReporteRendimiento(input);
    }

    @Test
    void createReporteRendimiento_exception() {
        ReporteRendimiento input = new ReporteRendimiento();
        when(reporteRendimientoService.saveReporteRendimiento(input)).thenThrow(new RuntimeException("error"));

        ResponseEntity<ReporteRendimiento> response = controller.createReporteRendimiento(input);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(reporteRendimientoService).saveReporteRendimiento(input);
    }

    @Test
    void getAllReportesRendimiento_returnsList() {
        List<ReporteRendimiento> reportes = Arrays.asList(new ReporteRendimiento(), new ReporteRendimiento());
        when(reporteRendimientoService.getAllReportesRendimiento()).thenReturn(reportes);

        ResponseEntity<List<ReporteRendimiento>> response = controller.getAllReportesRendimiento();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reportes, response.getBody());
        verify(reporteRendimientoService).getAllReportesRendimiento();
    }

    @Test
    void getAllReportesRendimiento_returnsNoContent() {
        when(reporteRendimientoService.getAllReportesRendimiento()).thenReturn(Collections.emptyList());

        ResponseEntity<List<ReporteRendimiento>> response = controller.getAllReportesRendimiento();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(reporteRendimientoService).getAllReportesRendimiento();
    }

    @Test
    void getReportesBySucursal_returnsList() {
        Long sucursalId = 1L;
        List<ReporteRendimiento> reportes = Arrays.asList(new ReporteRendimiento());
        when(reporteRendimientoService.getReportesBySucursal(any(Sucursal.class))).thenReturn(reportes);

        ResponseEntity<List<ReporteRendimiento>> response = controller.getReportesBySucursal(sucursalId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reportes, response.getBody());
        ArgumentCaptor<Sucursal> captor = ArgumentCaptor.forClass(Sucursal.class);
        verify(reporteRendimientoService).getReportesBySucursal(captor.capture());
        assertEquals(sucursalId, captor.getValue().getId());
    }

    @Test
    void getReportesBySucursal_returnsNoContent() {
        Long sucursalId = 2L;
        when(reporteRendimientoService.getReportesBySucursal(any(Sucursal.class))).thenReturn(Collections.emptyList());

        ResponseEntity<List<ReporteRendimiento>> response = controller.getReportesBySucursal(sucursalId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(reporteRendimientoService).getReportesBySucursal(any(Sucursal.class));
    }
}
