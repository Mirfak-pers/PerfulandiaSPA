package com.example.PerfulandiaSpa.asembled;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.PerfulandiaSpa.controller_HATEOAS.ReporteInventarioControllerV2;
import com.example.PerfulandiaSpa.model.ReporteInventario;



@Component
public class reporteInventarioModelAssembled implements RepresentationModelAssembler<ReporteInventario, EntityModel<ReporteInventario>> {

    @Override
    public EntityModel<ReporteInventario> toModel(ReporteInventario reporteInventario) {
        return EntityModel.of(
            reporteInventario,
            linkTo(methodOn(ReporteInventarioControllerV2.class).getReporteInventarioById(reporteInventario.getIdReporte())).withSelfRel(),
            linkTo(methodOn(ReporteInventarioControllerV2.class).getALL()).withRel("reportesInventario")
        );
    }


}