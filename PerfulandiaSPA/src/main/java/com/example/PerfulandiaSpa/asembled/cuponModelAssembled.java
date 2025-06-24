package com.example.PerfulandiaSpa.asembled;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.PerfulandiaSpa.controller_HATEOAS.CuponControllerV2;
import com.example.PerfulandiaSpa.model.Cupon;


@Component
public class cuponModelAssembled implements RepresentationModelAssembler<Cupon, EntityModel<Cupon>> {

    @Override
    public EntityModel<Cupon> toModel(Cupon cupon) {
        return EntityModel.of(
            cupon,
            linkTo(methodOn(CuponControllerV2.class).getCuponById(cupon.getId())).withSelfRel(),
            linkTo(methodOn(CuponControllerV2.class).getALL()).withRel("productos")
        );
    }


}