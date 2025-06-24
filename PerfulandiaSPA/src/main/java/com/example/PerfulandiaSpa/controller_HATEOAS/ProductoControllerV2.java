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

import com.example.PerfulandiaSpa.asembled.productoModelAssembled;
import com.example.PerfulandiaSpa.model.Producto;
import com.example.PerfulandiaSpa.services.ProductoService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/productosV2")
@Tag(name = "Producto V2", description = "Controlador de Productos V2")
public class ProductoControllerV2 {
    @Autowired
    private productoModelAssembled assembler;
    @Autowired
    private ProductoService productosService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //Recuperar todos los Productos
    public CollectionModel<EntityModel<Producto>> getALL(){
        List<EntityModel<Producto>> productos = productosService.getAllProductos()
        .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(productos,linkTo(methodOn(ProductoControllerV2.class)
        .getALL()).withSelfRel());
    }
    @GetMapping(value = "/{codigo}",produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Producto> getProductoById(@PathVariable int id) {
        Producto producto= productosService.getProductoById(id);
        return assembler.toModel(producto);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> saveProducto(@RequestBody Producto producto){
        Producto newProducto = productosService.saveProducto(producto);
        return ResponseEntity.created(linkTo(methodOn(ProductoControllerV2.class).getProductoById(newProducto.getId_producto())).toUri()).body(assembler.toModel(newProducto));
    }
    @PutMapping(value= "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> updateProducto(@PathVariable int codigo, @RequestBody Producto producto){
        producto.setId_producto(codigo);
        Producto prodUpdate = productosService.saveProducto(producto);
        return ResponseEntity.ok(assembler.toModel(prodUpdate));
    }
    @DeleteMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProducto(@PathVariable int codigo){
        productosService.deleteProducto(codigo);
        return ResponseEntity.noContent().build();
    }

}
