package com.example.PerfulandiaSpa.asembled;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.PerfulandiaSpa.controller_HATEOAS.SucursalControllerV2;
import com.example.PerfulandiaSpa.model.Sucursal;

@Component
public class sucursalModelAssembled implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(
            sucursal,
            linkTo(methodOn(SucursalControllerV2.class).getSucursalById(sucursal.getId())).withSelfRel(),
            linkTo(methodOn(SucursalControllerV2.class).getALL()).withRel("sucursales")
        );
    }


}