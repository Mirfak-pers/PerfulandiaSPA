
package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.Cupon;
import com.example.PerfulandiaSpa.services.CuponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;





class CuponControllerTest {

    @InjectMocks
    private CuponController cuponController;

    @Mock
    private CuponService cuponService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCupon() {
        Cupon cupon = new Cupon();
        when(cuponService.saveCupon(cupon)).thenReturn(cupon);

        ResponseEntity<Cupon> response = cuponController.createCupon(cupon);

        assertEquals(200, response.getStatusCode());
        assertEquals(cupon, response.getBody());
        verify(cuponService).saveCupon(cupon);
    }

    @Test
    void testGetAllCupones() {
        Cupon cupon1 = new Cupon();
        Cupon cupon2 = new Cupon();
        List<Cupon> cuponList = Arrays.asList(cupon1, cupon2);

        when(cuponService.getAllCupones()).thenReturn(cuponList);

        ResponseEntity<List<Cupon>> response = cuponController.getAllCupones();

        assertEquals(200, response.getStatusCode());
        assertEquals(cuponList, response.getBody());
        verify(cuponService).getAllCupones();
    }

    @Test
    void testGetCuponByIdFound() {
        Cupon cupon = new Cupon();
        when(cuponService.getCuponById(1L)).thenReturn(Optional.of(cupon));

        ResponseEntity<Cupon> response = cuponController.getCuponById(1L);

        assertEquals(200, response.getStatusCode());
        assertEquals(cupon, response.getBody());
        verify(cuponService).getCuponById(1L);
    }

    @Test
    void testGetCuponByIdNotFound() {
        when(cuponService.getCuponById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Cupon> response = cuponController.getCuponById(2L);

        assertEquals(404, response.getStatusCode());
        assertNull(response.getBody());
        verify(cuponService).getCuponById(2L);
    }

    @Test
    void testDeleteCupon() {
        doNothing().when(cuponService).deleteCupon(1L);

        ResponseEntity<Void> response = cuponController.deleteCupon(1L);

        assertEquals(204, response.getStatusCode());
        assertNull(response.getBody());
        verify(cuponService).deleteCupon(1L);
    }
}
