package com.example.PerfulandiaSpa.asembled;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.PerfulandiaSpa.controller_HATEOAS.EnvioControllerV2;

import com.example.PerfulandiaSpa.model.Envio;


@Component
public class envioModelAssembled implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(
            envio,
            //linkTo(methodOn(EnvioControllerV2.class).getEnvioById(envio.getId())).withSelfRel(),
            linkTo(methodOn(EnvioControllerV2.class).getALL()).withRel("envios")
        );
    }


}