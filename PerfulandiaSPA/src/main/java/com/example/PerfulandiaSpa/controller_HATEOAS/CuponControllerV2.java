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

import com.example.PerfulandiaSpa.asembled.cuponModelAssembled;

import com.example.PerfulandiaSpa.model.Cupon;

import com.example.PerfulandiaSpa.services.CuponService;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/cuponesV2")
@Tag(name = "Cupon V2", description = "Controlador de Cupones V2")
public class CuponControllerV2 {
    @Autowired
    private cuponModelAssembled assembler;
    @Autowired
    private CuponService cuponService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //Recuperar todos los Cupones
    public CollectionModel<EntityModel<Cupon>> getALL(){
        List<EntityModel<Cupon>> cupones = cuponService.getAllCupones()
        .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(cupones,linkTo(methodOn(CuponControllerV2.class)
        .getALL()).withSelfRel());
    }
    @GetMapping(value = "/{codigo}",produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Cupon> getCuponById(@PathVariable Long id) {
        Cupon cupon = cuponService.getCuponById(id)
            .orElseThrow(() -> new RuntimeException("Cupon not found with id: " + id));
        return assembler.toModel(cupon);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Cupon>> saveCupon(@RequestBody Cupon cupon){
        Cupon newCupon = cuponService.saveCupon(cupon);
        return ResponseEntity.created(linkTo(methodOn(CuponControllerV2.class).getCuponById(newCupon.getId())).toUri()).body(assembler.toModel(newCupon));
    }
    @PutMapping(value= "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Cupon>> updateCupon(@PathVariable Long codigo, @RequestBody Cupon cupon){
        cupon.setId(codigo);
        Cupon cuponUpdate = cuponService.saveCupon(cupon);
        return ResponseEntity.ok(assembler.toModel(cuponUpdate));
    }
    @DeleteMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCupon(@PathVariable Long codigo){
        cuponService.deleteCupon(codigo);
        return ResponseEntity.noContent().build();
    }

}
