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

import com.example.PerfulandiaSpa.asembled.envioDetalleModelAssembled;
import com.example.PerfulandiaSpa.model.EnvioDetalle;
import com.example.PerfulandiaSpa.services.EnvioDetalleService;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/enviosDetallesV2")
@Tag(name = "EnvioDetalle V2", description = "Controlador de EnvioDetalles V2")
public class EnvioDetalleControllerV2 {
    @Autowired
    private envioDetalleModelAssembled assembler;
    @Autowired
    private EnvioDetalleService envioDetalleService;


    //Recuperar todos los EnvioDetalles
    @GetMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<EnvioDetalle> getEnvioDetalleById(@PathVariable Long codigo) {
        EnvioDetalle envioDetalle = envioDetalleService.getEnvioDetalleById(codigo);
        return assembler.toModel(envioDetalle);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<EnvioDetalle>> saveEnvioDetalle(@RequestBody EnvioDetalle envioDetalle){
        EnvioDetalle newEnvioDetalle = envioDetalleService.saveEnvioDetalle(envioDetalle);
        return ResponseEntity.created(linkTo(methodOn(EnvioDetalleControllerV2.class).getEnvioDetalleById(newEnvioDetalle.getId())).toUri()).body(assembler.toModel(newEnvioDetalle));
    }
    @PutMapping(value= "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<EnvioDetalle>> updateEnvioDetalle(@PathVariable("codigo") Long codigo, @RequestBody EnvioDetalle envioDetalle){
        envioDetalle.setId(codigo);
        EnvioDetalle envUpdate = envioDetalleService.saveEnvioDetalle(envioDetalle);
        return ResponseEntity.ok(assembler.toModel(envUpdate));
    }
    @DeleteMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteEnvioDetalle(@PathVariable("codigo") Long codigo){
        envioDetalleService.deleteEnvioDetalle(codigo);
        return ResponseEntity.noContent().build();
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //Recuperar todos los EnvioDetalles
    public CollectionModel<EntityModel<EnvioDetalle>> getAll() {
        List<EntityModel<EnvioDetalle>> envioDetalles = envioDetalleService.getAllEnvioDetalles()
            .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(envioDetalles, linkTo(methodOn(EnvioDetalleControllerV2.class)
            .getAll()).withSelfRel());
    }

}
