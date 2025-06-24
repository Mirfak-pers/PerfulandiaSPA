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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PerfulandiaSpa.model.ReporteInventario;
import com.example.PerfulandiaSpa.services.ReporteInventarioService;
import com.example.PerfulandiaSpa.asembled.reporteInventarioModelAssembled;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/reportesInventarioV2")
@Tag(name = "Reporte de Inventario V2", description = "Controlador de Reportes de Inventario V2")
public class ReporteInventarioControllerV2 {
    @Autowired
    private reporteInventarioModelAssembled assembler;
    @Autowired
    private ReporteInventarioService reportesInventarioService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //Recuperar todos los Reportes de Inventario
    public CollectionModel<EntityModel<ReporteInventario>> getALL(){
        List<EntityModel<ReporteInventario>> reportesInventario = reportesInventarioService.getAllReportesInventario()
        .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(reportesInventario,linkTo(methodOn(ReporteInventarioControllerV2.class)
        .getALL()).withSelfRel());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<ReporteInventario>> saveReporteInventario(@RequestBody ReporteInventario reporteInventario){
        ReporteInventario newReporteInventario = reportesInventarioService.saveReporteInventario(reporteInventario);
        return ResponseEntity.created(linkTo(methodOn(ReporteInventarioControllerV2.class).getReporteInventarioById(newReporteInventario.getIdReporte())).toUri()).body(assembler.toModel(newReporteInventario));
    }
    @GetMapping(value = "/{idInventario}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<ReporteInventario> getReporteInventarioById(@PathVariable String idInventario) {
        ReporteInventario reporteInventario = reportesInventarioService.getReporteInventarioByID(idInventario);
        return assembler.toModel(reporteInventario);
    }

    @DeleteMapping(value = "/{idInventario}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteReporteInventario(@PathVariable String idInventario) {
        reportesInventarioService.deleteReporteInventario(idInventario);
        return ResponseEntity.noContent().build();
    }
}
