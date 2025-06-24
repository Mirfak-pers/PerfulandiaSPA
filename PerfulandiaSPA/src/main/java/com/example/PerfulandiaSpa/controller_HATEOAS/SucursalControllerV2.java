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


import com.example.PerfulandiaSpa.asembled.sucursalModelAssembled;
import com.example.PerfulandiaSpa.model.Sucursal;
import com.example.PerfulandiaSpa.services.SucursalService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/sucursalesV2")
@Tag(name = "Sucursal V2", description = "Controlador de Sucursales V2")
public class SucursalControllerV2 {
    @Autowired
    private sucursalModelAssembled assembler;
    @Autowired
    private SucursalService sucursalService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //Recuperar todos los Productos

    public CollectionModel<EntityModel<Sucursal>> getALL(){
        List<EntityModel<Sucursal>> sucursales = sucursalService.getAllSucursales()
        .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(sucursales,linkTo(methodOn(SucursalControllerV2.class)
        .getALL()).withSelfRel());
    }
    
    @GetMapping(value = "/{codigo}",produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Sucursal> getSucursalById(@PathVariable Long id) {
        Sucursal sucursal= sucursalService.getSucursalById(id);
        return assembler.toModel(sucursal);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> saveSucursal(@RequestBody Sucursal sucursal){
        Sucursal newSucursal = sucursalService.saveSucursal(sucursal);
        return ResponseEntity.created(linkTo(methodOn(SucursalControllerV2.class).getSucursalById(newSucursal.getId())).toUri()).body(assembler.toModel(newSucursal));
    }
    @PutMapping(value= "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> updateSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal){
        sucursal.setId(id);
        Sucursal sucursalUpdate = sucursalService.saveSucursal(sucursal);
        return ResponseEntity.ok(assembler.toModel(sucursalUpdate));
    }
    @DeleteMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteSucursal(@PathVariable Long id){
        sucursalService.deleteSucursal(id);
        return ResponseEntity.noContent().build();
    }

}
