package com.example.PerfulandiaSpa.services;

import com.example.PerfulandiaSpa.model.Carrito;
import com.example.PerfulandiaSpa.model.ItemCarrito;
import com.example.PerfulandiaSpa.model.Producto;
import com.example.PerfulandiaSpa.model.Usuario;
import com.example.PerfulandiaSpa.repository.CarritoRepository;
import com.example.PerfulandiaSpa.repository.CarritoRepositoryJpa;
import com.example.PerfulandiaSpa.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

@Autowired
private ProductoRepository productoRepository; // Asegúrate de tener este repository

@Autowired
private CarritoRepository carritoRepository; // Inyecta el CarritoRepository
private CarritoRepositoryJpa carritoRepositoryJpa;

public Carrito agregarItem(Carrito carrito, ItemCarrito item, Usuario idUsuario) {
    // Recupera el producto por ID
    int productoId = item.getProducto().getId_producto();
    Producto producto = productoRepository.buscarPorId(productoId)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    item.setProducto(producto);

    Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioId(carrito.getUsuario().getId());
    Carrito carritoExistente = carritoOpt.orElse(new Carrito());
    carritoExistente.getItems().add(item);
    carritoExistente.setUsuario(idUsuario);
    return carritoRepository.save(carritoExistente);
}

    public Optional<Carrito> obtenerCarrito(Long usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId);
    }

    public void vaciarCarrito(Long usuarioId) {
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioId(usuarioId);
        carritoOpt.ifPresent(carrito -> carritoRepository.deleteByUsuarioId(carrito.getId()));
    }
        public Carrito getCarritoById(Long id) {
        return carritoRepositoryJpa.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con ID: " + id));
    }
        public List<Carrito> getAllCarritos() {
        return carritoRepositoryJpa.findAll();
    }
}