package com.example.PerfulandiaSpa.asembled;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


import com.example.PerfulandiaSpa.controller_HATEOAS.PedidoControllerV2;

// TODO: Update the import below to match the actual package and class location of PedidoControllerV2
// For example, if the correct package is 'com.example.PerfulandiaSpa.controller', use:

import com.example.PerfulandiaSpa.model.Pedido;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
public class pedidoModelAssembled implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(
            pedido,
            linkTo(methodOn(PedidoControllerV2.class).getPedidoById(pedido.getId())).withSelfRel(),
            linkTo(methodOn(PedidoControllerV2.class).getALL()).withRel("pedidos")
        );
    }


}