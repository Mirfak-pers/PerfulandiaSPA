package com.example.PerfulandiaSpa.controller;

import com.example.PerfulandiaSpa.model.Carrito;
import com.example.PerfulandiaSpa.model.ItemCarrito;
import com.example.PerfulandiaSpa.model.Usuario;
import com.example.PerfulandiaSpa.services.CarritoService;
import com.example.PerfulandiaSpa.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;





class CarritoControllerTest {

    @Mock
    private CarritoService carritoService;
    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private CarritoController carritoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void agregarItem_usuarioExiste_itemAgregado() {
        Long usuarioId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        ItemCarrito item = new ItemCarrito();
        Carrito carrito = new Carrito();
        carrito.setUsuario(usuario);

        when(usuarioService.getUsuarioById(usuarioId)).thenReturn(usuario);
        when(carritoService.obtenerCarrito(usuarioId)).thenReturn(Optional.of(carrito));
        when(carritoService.agregarItem(any(Carrito.class), any(ItemCarrito.class), any(Usuario.class))).thenReturn(carrito);

        ResponseEntity<Carrito> response = carritoController.agregarItem(usuarioId, item);

        assertEquals(200, response.getStatusCode());
        assertEquals(carrito, response.getBody());
        verify(carritoService).agregarItem(any(Carrito.class), any(ItemCarrito.class), eq(usuario));
    }

    @Test
    void agregarItem_usuarioNoExiste_retornaNotFound() {
        Long usuarioId = 2L;
        ItemCarrito item = new ItemCarrito();

        when(usuarioService.getUsuarioById(usuarioId)).thenReturn(null);

        ResponseEntity<Carrito> response = carritoController.agregarItem(usuarioId, item);

        assertEquals(404, response.getStatusCode());
        assertNull(response.getBody());
        verify(carritoService, never()).agregarItem(any(), any(), any());
    }

    @Test
    void agregarItem_excepcionInterna_retorna500() {
        Long usuarioId = 3L;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        ItemCarrito item = new ItemCarrito();

        when(usuarioService.getUsuarioById(usuarioId)).thenReturn(usuario);
        when(carritoService.obtenerCarrito(usuarioId)).thenThrow(new RuntimeException("DB error"));

        ResponseEntity<Carrito> response = carritoController.agregarItem(usuarioId, item);

        assertEquals(500, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void obtenerCarrito_carritoExiste_retornaCarrito() {
        Long usuarioId = 4L;
        Carrito carrito = new Carrito();

        when(carritoService.obtenerCarrito(usuarioId)).thenReturn(Optional.of(carrito));

        ResponseEntity<Carrito> response = carritoController.obtenerCarrito(usuarioId);

        assertEquals(200, response.getStatusCode());
        assertEquals(carrito, response.getBody());
    }

    @Test
    void obtenerCarrito_carritoNoExiste_retornaNotFound() {
        Long usuarioId = 5L;

        when(carritoService.obtenerCarrito(usuarioId)).thenReturn(Optional.empty());

        ResponseEntity<Carrito> response = carritoController.obtenerCarrito(usuarioId);

        assertEquals(404, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void vaciarCarrito_llamaServicioYRetornaNoContent() {
        Long usuarioId = 6L;

        ResponseEntity<Void> response = carritoController.vaciarCarrito(usuarioId);

        assertEquals(204, response.getStatusCode());
        verify(carritoService).vaciarCarrito(usuarioId);
    }
}

