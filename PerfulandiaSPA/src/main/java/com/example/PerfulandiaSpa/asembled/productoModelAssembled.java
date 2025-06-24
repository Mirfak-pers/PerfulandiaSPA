package com.example.PerfulandiaSpa.asembled;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.PerfulandiaSpa.controller_HATEOAS.ProductoControllerV2;
import com.example.PerfulandiaSpa.model.Producto;

@Component
public class productoModelAssembled implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(
            producto,
            linkTo(methodOn(ProductoControllerV2.class).getProductoById(producto.getId_producto())).withSelfRel(),
            linkTo(methodOn(ProductoControllerV2.class).getALL()).withRel("productos")
        );
    }


}