package com.example.PerfulandiaSpa.controller_HATEOAS;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PerfulandiaSpa.asembled.usuarioModelAssembled;
import com.example.PerfulandiaSpa.model.Usuario;
import com.example.PerfulandiaSpa.services.UsuarioService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/usuariosV2")
@Tag(name = "Usuario V2", description = "Controlador de Usuarios V2")
public class UsuarioControllerV2{
    @Autowired
    private usuarioModelAssembled assembler;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<Usuario>> getALL(){
        List<EntityModel<Usuario>> usuarios = usuarioService.getAllUsuarios()
        .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(usuarios,linkTo(methodOn(UsuarioControllerV2.class)
        .getALL()).withSelfRel());
    }
    @GetMapping(value = "/{codigo}",produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Usuario> getUsuarioById(@PathVariable Long id) {
        Usuario usuario= usuarioService.getUsuarioById(id);
        return assembler.toModel(usuario);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> saveUsuario(@RequestBody Usuario usuario){
        Usuario newUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.created(linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(newUsuario.getId())).toUri()).body(assembler.toModel(newUsuario));
    }
    @PutMapping(value= "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(@PathVariable long codigo, @RequestBody Usuario usuario){
        usuario.setId(codigo);
        Usuario prodUpdate = usuarioService.saveUsuario(usuario);
        return ResponseEntity.ok(assembler.toModel(prodUpdate));
    }
    @DeleteMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUsuario(@PathVariable long codigo){
        usuarioService.deleteUsuario(codigo);
        return ResponseEntity.noContent().build();
    }

}
