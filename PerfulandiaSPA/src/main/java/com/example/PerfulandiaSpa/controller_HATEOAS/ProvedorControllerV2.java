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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PerfulandiaSpa.asembled.proveedorModelAssembled;
import com.example.PerfulandiaSpa.services.ProvedorService;
import com.example.PerfulandiaSpa.model.Provedor;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/proveedoresV2")
@Tag(name = "Proveedor V2", description = "Controlador de Proveedores V2")
public class ProvedorControllerV2 {
    @Autowired
    private proveedorModelAssembled assembler;
    @Autowired
    private ProvedorService proveedorService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<Provedor>> getALL() {
        List<EntityModel<Provedor>> proveedores = proveedorService.getAllProvedores()
            .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(proveedores, linkTo(methodOn(ProvedorControllerV2.class)
            .getALL()).withSelfRel());
    }

    @GetMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Provedor> getProveedorById(@PathVariable("codigo") int codigo) {
        Provedor proveedor = proveedorService.getProvedorById(codigo);
        return assembler.toModel(proveedor);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Provedor>> saveProveedor(@RequestBody Provedor proveedor) {
        Provedor newProveedor = proveedorService.saveProvedor(proveedor);
        return ResponseEntity.created(linkTo(methodOn(ProvedorControllerV2.class)
            .getProveedorById(newProveedor.getId())).toUri()).body(assembler.toModel(newProveedor));
    }

    @PutMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Provedor>> updateProveedor(@PathVariable("codigo") int codigo, @RequestBody Provedor proveedor) {
        proveedor.setId(codigo);
        Provedor prodUpdate = proveedorService.saveProvedor(proveedor);
        return ResponseEntity.ok(assembler.toModel(prodUpdate));
    }

    @DeleteMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProveedor(@PathVariable("codigo") int codigo) {
        proveedorService.deleteProvedor(codigo);
        return ResponseEntity.noContent().build();
    }
}