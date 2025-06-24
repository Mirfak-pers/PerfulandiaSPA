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

import com.example.PerfulandiaSpa.model.ReporteRendimiento;
import com.example.PerfulandiaSpa.services.ReporteRendimientoService;
import com.example.PerfulandiaSpa.asembled.reporteRendimientoModelAssembled;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/reportesRendimientoV2")
@Tag(name = "Reporte de Rendimiento V2", description = "Controlador de Reportes de Rendimiento V2")
public class ReporteRendimientoControllerV2 {
    @Autowired
    private reporteRendimientoModelAssembled assembler;
    @Autowired
    private ReporteRendimientoService reportesRendimientoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //Recuperar todos los Reportes de Rendimiento
    public CollectionModel<EntityModel<ReporteRendimiento>> getALL(){
        List<EntityModel<ReporteRendimiento>> reportesRendimiento = reportesRendimientoService.getAllReportesRendimiento()
        .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(reportesRendimiento,linkTo(methodOn(ReporteRendimientoControllerV2.class)
        .getALL()).withSelfRel());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<ReporteRendimiento>> saveReporteRendimiento(@RequestBody ReporteRendimiento reporteRendimiento){
        ReporteRendimiento newReporteRendimiento = reportesRendimientoService.saveReporteRendimiento(reporteRendimiento);
        return ResponseEntity.created(linkTo(methodOn(ReporteRendimientoControllerV2.class).getReporteRendimientoById(newReporteRendimiento.getSucursal().getId())).toUri()).body(assembler.toModel(newReporteRendimiento));
    }

    @GetMapping(value = "/{idSucursal}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<ReporteRendimiento> getReporteRendimientoById(@PathVariable Long idSucursal) {
        List<ReporteRendimiento> reportes = reportesRendimientoService.getReportesById(idSucursal);
        if (reportes == null || reportes.isEmpty()) {
            throw new RuntimeException("Reporte de rendimiento no encontrado para la sucursal: " + idSucursal);
        }
        ReporteRendimiento reporteRendimiento = reportes.get(0); // O selecciona el que corresponda
        return assembler.toModel(reporteRendimiento);
    }

    @DeleteMapping(value = "/{idSucursal}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteReporteRendimiento(@PathVariable Long idSucursal) {
        reportesRendimientoService.deleteReporteRendimiento(idSucursal);
        return ResponseEntity.noContent().build();
    }
}
