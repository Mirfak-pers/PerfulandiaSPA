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


import com.example.PerfulandiaSpa.asembled.ventaModelAssembled;
import com.example.PerfulandiaSpa.model.Venta;
import com.example.PerfulandiaSpa.services.VentaService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/ventaV2")
@Tag(name = "Producto V2", description = "Controlador de Productos V2")
public class VentaControllerV2 {
    @Autowired
    private ventaModelAssembled assembler;
    @Autowired
    private VentaService ventaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<Venta>> getALL(){
        List<EntityModel<Venta>> ventas = ventaService  .getAllVentas()
        .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(ventas,linkTo(methodOn(VentaControllerV2.class)
        .getALL()).withSelfRel());
    }
    @GetMapping(value = "/{codigo}",produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Venta> getVentaById(@PathVariable Long id) {
        Venta venta= ventaService.getVentaById(id);
        return assembler.toModel(venta);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Venta>> saveVenta(@RequestBody Venta venta){
        Venta newVenta = ventaService.saveVenta(venta);
        return ResponseEntity.created(linkTo(methodOn(VentaControllerV2.class).getVentaById(newVenta.getId())).toUri()).body(assembler.toModel(newVenta));
    }
    @PutMapping(value= "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Venta>> updateVenta(@PathVariable long codigo, @RequestBody Venta venta){
        venta.setId(codigo);
        Venta prodUpdate = ventaService.saveVenta(venta);
        return ResponseEntity.ok(assembler.toModel(prodUpdate));
    }
    @DeleteMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteVenta(@PathVariable long codigo){
        ventaService.deleteVenta(codigo);
        return ResponseEntity.noContent().build();
    }

}
