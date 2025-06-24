package com.example.PerfulandiaSpa.asembled;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.PerfulandiaSpa.controller_HATEOAS.VentaControllerV2;

import com.example.PerfulandiaSpa.model.Venta;

@Component
public class ventaModelAssembled implements RepresentationModelAssembler<Venta, EntityModel<Venta>> {

    @Override
    public EntityModel<Venta> toModel(Venta venta) {
        return EntityModel.of(
            venta,
            linkTo(methodOn(VentaControllerV2.class).getVentaById(venta.getId())).withSelfRel(),
            linkTo(methodOn(VentaControllerV2.class).getALL()).withRel("ventas")
        );
    }


}