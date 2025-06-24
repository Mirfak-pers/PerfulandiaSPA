package com.example.PerfulandiaSpa.asembled;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.PerfulandiaSpa.controller_HATEOAS.ProvedorControllerV2;
import com.example.PerfulandiaSpa.model.Provedor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
public class proveedorModelAssembled implements RepresentationModelAssembler<Provedor, EntityModel<Provedor>>{
    @Override
    public EntityModel<Provedor> toModel(Provedor provedor){
        return EntityModel.of(
            provedor,
            linkTo(methodOn(ProvedorControllerV2.class).getProveedorById(provedor.getId())).withSelfRel(),
            linkTo(methodOn(ProvedorControllerV2.class).getALL()).withRel("proveedores")
        );
    }
}
