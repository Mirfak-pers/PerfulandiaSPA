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



import com.example.PerfulandiaSpa.model.ReporteVenta;
import com.example.PerfulandiaSpa.services.ReporteVentaService;
import com.example.PerfulandiaSpa.asembled.reporteVentasModelAssembled;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/reportesVentasV2")
@Tag(name = "Reporte de Ventas V2", description = "Controlador de Reportes de Ventas V2")
public class ReporteVentaControllerV2 {
    @Autowired
    private reporteVentasModelAssembled assembler;
    @Autowired
    private ReporteVentaService reportesVentasService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //Recuperar todos los Reportes de Ventas
    public CollectionModel<EntityModel<ReporteVenta>> getALL(){
        List<EntityModel<ReporteVenta>> reportesVentas = reportesVentasService.getAllReportesVenta()
        .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(reportesVentas,linkTo(methodOn(ReporteVentaControllerV2.class)
        .getALL()).withSelfRel());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<ReporteVenta>> saveReporteVenta(@RequestBody ReporteVenta reporteVenta){
        ReporteVenta newReporteVenta = reportesVentasService.saveReporteVenta(reporteVenta);
        return ResponseEntity.created(linkTo(methodOn(ReporteVentaControllerV2.class).getReporteVentaById(newReporteVenta.getIdVenta())).toUri()).body(assembler.toModel(newReporteVenta));
    }
    @GetMapping(value = "/{idVenta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<ReporteVenta> getReporteVentaById(@PathVariable String idVenta) {
        ReporteVenta reporteVenta = reportesVentasService.getReporteVentaById(idVenta);
        return assembler.toModel(reporteVenta);
    }

    @DeleteMapping(value = "/{idVenta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteReporteVenta(@PathVariable String idVenta) {
        reportesVentasService.deleteReporteVenta(idVenta);
        return ResponseEntity.noContent().build();
    }

    
}
