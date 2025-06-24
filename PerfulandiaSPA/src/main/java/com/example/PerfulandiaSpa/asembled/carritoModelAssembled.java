package com.example.PerfulandiaSpa.asembled;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.PerfulandiaSpa.controller_HATEOAS.ProductoControllerV2;
import com.example.PerfulandiaSpa.model.Carrito;


@Component
public class carritoModelAssembled implements RepresentationModelAssembler<Carrito, EntityModel<Carrito>> {
    @Override
    @NonNull
    public EntityModel<Carrito> toModel(@NonNull Carrito carrito) {
        return EntityModel.of(
            carrito,
            linkTo(methodOn(ProductoControllerV2.class).getALL()).withRel("productos")
            //, linkTo(methodOn(ProductoControllerV2.class).getProductoById(carrito.getId())).withSelfRel()
        );
    }
}


