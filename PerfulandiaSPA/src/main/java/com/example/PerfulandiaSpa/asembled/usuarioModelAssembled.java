package com.example.PerfulandiaSpa.asembled;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.PerfulandiaSpa.controller_HATEOAS.UsuarioControllerV2;
import com.example.PerfulandiaSpa.model.Usuario;

@Component
public class usuarioModelAssembled implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(
            usuario,
            linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(usuario.getId())).withSelfRel(),
            linkTo(methodOn(UsuarioControllerV2.class).getALL()).withRel("usuarios")
        );
    }


}