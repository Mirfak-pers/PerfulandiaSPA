package com.example.PerfulandiaSpa.services;

import com.example.PerfulandiaSpa.model.Pedido;
import com.example.PerfulandiaSpa.model.Sucursal;
import com.example.PerfulandiaSpa.model.Usuario;
import com.example.PerfulandiaSpa.repository.PedidoRepository;
import com.example.PerfulandiaSpa.repository.PedidoRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepositoryJPA pedidoRepositoryjpa;
    private PedidoRepository pedidoRepository;
    public List<Pedido> getAllPedidos() {
        return pedidoRepositoryjpa.findAll();
    }

    public Pedido getPedidoById(int id) {
        return pedidoRepositoryjpa.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    public Pedido savePedido(Pedido pedido) {
        return pedidoRepositoryjpa.save(pedido);
    }

    public void deletePedido(int id) {
        pedidoRepositoryjpa.deleteById(id);
    }

    public List<Pedido> getPedidosByUsuario(Usuario usuario) {
        return pedidoRepository.findByUsuario(usuario);
    }

    public List<Pedido> getPedidosBySucursal(Sucursal sucursal) {
        return pedidoRepository.findBySucursal(sucursal);
    }

    
}
