package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.Provedor;
import com.example.PerfulandiaSpa.services.ProvedorService;

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






class ProvedorControllerTest {

    @InjectMocks
    private ProvedorController provedorController;

    @Mock
    private ProvedorService provedorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProvedores_ReturnsList() {
        Provedor p1 = new Provedor();
        Provedor p2 = new Provedor();
        when(provedorService.getAllProvedores()).thenReturn(Arrays.asList(p1, p2));

        ResponseEntity<List<Provedor>> response = provedorController.getAllProvedores();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getAllProvedores_ReturnsNoContent() {
        when(provedorService.getAllProvedores()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Provedor>> response = provedorController.getAllProvedores();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getProvedorById_ReturnsProvedor() {
        Provedor p = new Provedor();
        when(provedorService.getProvedorById(1)).thenReturn(p);

        ResponseEntity<Provedor> response = provedorController.getProvedorById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(p, response.getBody());
    }

    @Test
    void getProvedorById_NotFound() {
        when(provedorService.getProvedorById(1)).thenThrow(new RuntimeException());

        ResponseEntity<Provedor> response = provedorController.getProvedorById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void createProvedor_ReturnsCreated() {
        Provedor p = new Provedor();
        when(provedorService.saveProvedor(p)).thenReturn(p);

        ResponseEntity<Provedor> response = provedorController.createProvedor(p);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(p, response.getBody());
    }

    @Test
    void updateProvedor_ReturnsUpdated() {
        Provedor existing = new Provedor();
        existing.setNombre("Old");
        Provedor update = new Provedor();
        update.setNombre("New");
        update.setDireccion("Dir");
        update.setEmail("mail");
        update.setTelefono("tel");
        update.setContacto("cont");

        when(provedorService.getProvedorById(1)).thenReturn(existing);
        when(provedorService.saveProvedor(any(Provedor.class))).thenReturn(existing);

        ResponseEntity<Provedor> response = provedorController.updateProvedor(1, update);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("New", response.getBody().getNombre());
    }

    @Test
    void updateProvedor_NotFound() {
        Provedor update = new Provedor();
        when(provedorService.getProvedorById(1)).thenThrow(new RuntimeException());

        ResponseEntity<Provedor> response = provedorController.updateProvedor(1, update);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteProvedor_ReturnsOk() {
        doNothing().when(provedorService).deleteProvedor(1);

        ResponseEntity<String> response = provedorController.deleteProvedor(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Provedor eliminado exitosamente.", response.getBody());
    }

    @Test
    void deleteProvedor_NotFound() {
        doThrow(new RuntimeException()).when(provedorService).deleteProvedor(1);

        ResponseEntity<String> response = provedorController.deleteProvedor(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Provedor no encontrado con ID: 1", response.getBody());
    }
}