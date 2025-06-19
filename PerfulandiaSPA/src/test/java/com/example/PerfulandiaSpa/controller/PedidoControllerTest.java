package com.example.PerfulandiaSpa.controller;


import com.example.PerfulandiaSpa.model.Pedido;
import com.example.PerfulandiaSpa.model.Usuario;
import com.example.PerfulandiaSpa.model.Sucursal;
import com.example.PerfulandiaSpa.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;




class PedidoControllerTest {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPedidos_returnsPedidosList() {
        List<Pedido> pedidos = Arrays.asList(new Pedido(), new Pedido());
        when(pedidoService.getAllPedidos()).thenReturn(pedidos);

        ResponseEntity<List<Pedido>> response = pedidoController.getAllPedidos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getAllPedidos_returnsNoContent() {
        when(pedidoService.getAllPedidos()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Pedido>> response = pedidoController.getAllPedidos();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getPedidoById_returnsPedido() {
        Pedido pedido = new Pedido();
        when(pedidoService.getPedidoById(1)).thenReturn(pedido);

        ResponseEntity<Pedido> response = pedidoController.getPedidoById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedido, response.getBody());
    }

    @Test
    void getPedidoById_notFound() {
        when(pedidoService.getPedidoById(1)).thenThrow(new RuntimeException());

        ResponseEntity<Pedido> response = pedidoController.getPedidoById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getPedidosByUsuario_returnsPedidosList() {
        Usuario usuario = new Usuario();
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        List<Pedido> pedidos = Arrays.asList(pedido);
        when(pedidoService.getPedidosByUsuario(usuario)).thenReturn(pedidos);

        ResponseEntity<List<Pedido>> response = pedidoController.getPedidosByUsuario(pedido);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidos, response.getBody());
    }

    @Test
    void getPedidosByUsuario_returnsNoContent() {
        Usuario usuario = new Usuario();
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        when(pedidoService.getPedidosByUsuario(usuario)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Pedido>> response = pedidoController.getPedidosByUsuario(pedido);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getPedidosBySucursal_returnsPedidosList() {
        Sucursal sucursal = new Sucursal();
        Pedido pedido = new Pedido();
        pedido.setSucursal(sucursal);
        List<Pedido> pedidos = Arrays.asList(pedido);
        when(pedidoService.getPedidosBySucursal(sucursal)).thenReturn(pedidos);

        ResponseEntity<List<Pedido>> response = pedidoController.getPedidosBySucursal(pedido);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidos, response.getBody());
    }

    @Test
    void getPedidosBySucursal_returnsNoContent() {
        Sucursal sucursal = new Sucursal();
        Pedido pedido = new Pedido();
        pedido.setSucursal(sucursal);
        when(pedidoService.getPedidosBySucursal(sucursal)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Pedido>> response = pedidoController.getPedidosBySucursal(pedido);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void createPedido_returnsCreatedPedido() {
        Pedido pedido = new Pedido();
        when(pedidoService.savePedido(pedido)).thenReturn(pedido);

        ResponseEntity<Pedido> response = pedidoController.createPedido(pedido);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pedido, response.getBody());
    }

    @Test
    void updatePedido_success() {
        Pedido pedido = new Pedido();
        pedido.setEstado("nuevo");
        pedido.setFechaCreacion(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
        pedido.setProductos(new ArrayList<>());

        Pedido existing = new Pedido();
        when(pedidoService.getPedidoById(1)).thenReturn(existing);
        when(pedidoService.savePedido(existing)).thenReturn(existing);

        ResponseEntity<Pedido> response = pedidoController.updatePedido(1, pedido);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existing, response.getBody());
    }

    @Test
    void updatePedido_notFound() {
        Pedido pedido = new Pedido();
        when(pedidoService.getPedidoById(1)).thenThrow(new RuntimeException());

        ResponseEntity<Pedido> response = pedidoController.updatePedido(1, pedido);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deletePedido_success() {
        doNothing().when(pedidoService).deletePedido(1);

        ResponseEntity<String> response = pedidoController.deletePedido(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pedido eliminado exitosamente.", response.getBody());
    }

    @Test
    void deletePedido_notFound() {
        doThrow(new RuntimeException()).when(pedidoService).deletePedido(1);

        ResponseEntity<String> response = pedidoController.deletePedido(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("Pedido no encontrado"));
    }
}
