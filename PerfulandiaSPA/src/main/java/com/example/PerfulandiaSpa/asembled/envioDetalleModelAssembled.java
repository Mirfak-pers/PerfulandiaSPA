package com.example.PerfulandiaSpa.asembled;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.PerfulandiaSpa.controller_HATEOAS.EnvioDetalleControllerV2;
import com.example.PerfulandiaSpa.model.EnvioDetalle;

@Component
public class envioDetalleModelAssembled implements RepresentationModelAssembler<EnvioDetalle, EntityModel<EnvioDetalle>> {

    @Override
    public EntityModel<EnvioDetalle> toModel(EnvioDetalle envioDetalle) {
        return EntityModel.of(
            envioDetalle,
            linkTo(methodOn(EnvioDetalleControllerV2.class).getEnvioDetalleById(envioDetalle.getId())).withSelfRel(),
            linkTo(methodOn(EnvioDetalleControllerV2.class).getAll()).withRel("enviosDetalles")
        );
    }


}