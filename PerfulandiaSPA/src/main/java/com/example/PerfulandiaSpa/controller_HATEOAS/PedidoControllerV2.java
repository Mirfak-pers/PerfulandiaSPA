package com.example.PerfulandiaSpa.controller_HATEOAS;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PerfulandiaSpa.asembled.pedidoModelAssembled;
import com.example.PerfulandiaSpa.model.Pedido;
import com.example.PerfulandiaSpa.services.PedidoService;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/pedidosV2")
@Tag(name = "Pedido V2", description = "Controlador de Pedidos V2")

public class PedidoControllerV2  {
    @Autowired
    private pedidoModelAssembled assembler;
    @Autowired
    private PedidoService productosService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //Recuperar todos los Pedidos
    public CollectionModel<EntityModel<Pedido>> getALL(){
        List<EntityModel<Pedido>> pedidos = productosService.getAllPedidos()
        .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(pedidos,linkTo(methodOn(PedidoControllerV2.class)
        .getALL()).withSelfRel());
    }
    @GetMapping(value = "/{codigo}",produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Pedido> getPedidoById(@PathVariable int id) {
        Pedido pedido= productosService.getPedidoById(id);
        return assembler.toModel(pedido);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Pedido>> savePedido(@RequestBody Pedido pedido){
        Pedido newPedido = productosService.savePedido(pedido);
        return ResponseEntity.created(linkTo(methodOn(PedidoControllerV2.class).getPedidoById(newPedido.getId())).toUri()).body(assembler.toModel(newPedido));
    }
    @PutMapping(value= "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Pedido>> updatePedido(@PathVariable int codigo, @RequestBody Pedido pedido){
        pedido.setId(codigo);
        Pedido prodUpdate = productosService.savePedido(pedido);
        return ResponseEntity.ok(assembler.toModel(prodUpdate));
    }
    @DeleteMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePedido(@PathVariable int codigo){
        productosService.deletePedido(codigo);
        return ResponseEntity.noContent().build();
    }

}
