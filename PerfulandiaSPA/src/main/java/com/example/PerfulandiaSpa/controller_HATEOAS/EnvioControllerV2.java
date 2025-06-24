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

import com.example.PerfulandiaSpa.asembled.envioModelAssembled;

import com.example.PerfulandiaSpa.model.Envio;

import com.example.PerfulandiaSpa.services.EnvioService;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/enviosV2")
@Tag(name = "Envio V2", description = "Controlador de Envios V2")
public class EnvioControllerV2 {
    @Autowired
    private envioModelAssembled assembler;
    @Autowired
    private EnvioService enviosService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //Recuperar todos los Envios
    public CollectionModel<EntityModel<Envio>> getALL(){
        List<EntityModel<Envio>> envios = enviosService.getAllEnvios()
        .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(envios,linkTo(methodOn(EnvioControllerV2.class)
        .getALL()).withSelfRel());
    }
    @GetMapping(value = "/{codigo}",produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Envio> getEnvioById(@PathVariable int id) {
        Envio envio= enviosService.getEnvioById(id);
        return assembler.toModel(envio );
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Envio>> saveEnvio(@RequestBody Envio envio){
        Envio newEnvio = enviosService.saveEnvio(envio);
        return ResponseEntity.created(linkTo(methodOn(EnvioControllerV2.class).getEnvioById(newEnvio.getId().intValue())).toUri()).body(assembler.toModel(newEnvio));
    }
    @PutMapping(value= "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Envio>> updateEnvio(@PathVariable Long codigo, @RequestBody Envio envio){
        envio.setId(codigo);
        Envio envUpdate = enviosService.saveEnvio(envio);
        return ResponseEntity.ok(assembler.toModel(envUpdate));
    }
    @DeleteMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteEnvio(@PathVariable int codigo){
        enviosService.deleteEnvio(codigo);
        return ResponseEntity.noContent().build();
    }

}
