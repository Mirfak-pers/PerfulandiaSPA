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

import com.example.PerfulandiaSpa.asembled.carritoModelAssembled;
import com.example.PerfulandiaSpa.model.Carrito;
import com.example.PerfulandiaSpa.model.ItemCarrito;
import com.example.PerfulandiaSpa.model.Usuario;
import com.example.PerfulandiaSpa.services.CarritoService;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/carritosV2")
@Tag(name = "Carrito V2", description = "Controlador de Carritos V2")
public class CarritoControllerV2 {
    @Autowired
    private carritoModelAssembled assembler;
    @Autowired
    private CarritoService carritoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //Recuperar todos los Carritos
    public CollectionModel<EntityModel<Carrito>> getALL(){
        List<EntityModel<Carrito>> carritos = carritoService.getAllCarritos()
        .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(carritos,linkTo(methodOn(CarritoControllerV2.class)
        .getALL()).withSelfRel());
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Carrito>> saveCarrito(@RequestBody Carrito carrito){
        ItemCarrito item = null;  
        Usuario usuario = null;
        Carrito newCarrito = carritoService.agregarItem(carrito, item, usuario);
        return ResponseEntity.created(linkTo(methodOn(CarritoControllerV2.class).getCarritoById(newCarrito.getId())).toUri()).body(assembler.toModel(newCarrito));
    }
    @PutMapping(value= "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Carrito>> updateCarrito(@PathVariable Long codigo, @RequestBody Carrito carrito){
        carrito.setId(codigo);
        ItemCarrito item = null;
        Usuario usuario = null;
        Carrito carritoUpdate = carritoService.agregarItem(carrito, item, usuario);
        return ResponseEntity.ok(assembler.toModel(carritoUpdate));
    }
    @DeleteMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCarrito(@PathVariable Long codigo){
        carritoService.vaciarCarrito(codigo);
        return ResponseEntity.noContent().build();
    }
    @GetMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Carrito> getCarritoById(@PathVariable Long codigo) {
        Carrito carrito = carritoService.getCarritoById(codigo);
        return assembler.toModel(carrito);

    }
}
