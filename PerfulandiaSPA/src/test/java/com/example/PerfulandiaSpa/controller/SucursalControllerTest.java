package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.Sucursal;
import com.example.PerfulandiaSpa.services.SucursalService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SucursalControllerTest {

    @Mock
    private SucursalService sucursalService;

    @InjectMocks
    private SucursalController sucursalController;

    @Test
    void testGetAllSucursales_ReturnsList() {
        List<Sucursal> sucursales = Arrays.asList(new Sucursal(), new Sucursal());
        when(sucursalService.getAllSucursales()).thenReturn(sucursales);

        ResponseEntity<List<Sucursal>> response = sucursalController.getAllSucursales();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetSucursalById_Found() {
        Sucursal sucursal = new Sucursal();
        when(sucursalService.getSucursalById(1L)).thenReturn(sucursal);

        ResponseEntity<Sucursal> response = sucursalController.getSucursalById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sucursal, response.getBody());
    }


    @Test
    void testCreateSucursal() {
        Sucursal input = new Sucursal();
        Sucursal saved = new Sucursal();
        when(sucursalService.saveSucursal(input)).thenReturn(saved);

        ResponseEntity<Sucursal> response = sucursalController.createSucursal(input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(saved, response.getBody());
    }

    @Test
    void testUpdateSucursal_Success() {
        Sucursal existing = new Sucursal();
        existing.setNombre("Old");
        existing.setDireccion("Old Dir");
        Sucursal update = new Sucursal();
        update.setNombre("New");
        update.setDireccion("New Dir");
        Sucursal updated = new Sucursal();
        updated.setNombre("New");
        updated.setDireccion("New Dir");

        when(sucursalService.getSucursalById(1L)).thenReturn(existing);
        when(sucursalService.saveSucursal(existing)).thenReturn(updated);

        ResponseEntity<Sucursal> response = sucursalController.updateSucursal(1L, update);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("New", response.getBody().getNombre());
        assertEquals("New Dir", response.getBody().getDireccion());
    }

    @Test
    void testDeleteSucursal_Success() {
        doNothing().when(sucursalService).deleteSucursal(1L);

        ResponseEntity<String> response = sucursalController.deleteSucursal(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sucursal eliminada exitosamente.", response.getBody());
    }

}
