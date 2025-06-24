package com.example.PerfulandiaSpa.asembled;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.PerfulandiaSpa.controller_HATEOAS.ReporteVentaControllerV2;
import com.example.PerfulandiaSpa.model.ReporteVenta;

@Component
public class reporteVentasModelAssembled implements RepresentationModelAssembler<ReporteVenta, EntityModel<ReporteVenta>> {

    @Override
    public EntityModel<ReporteVenta> toModel(ReporteVenta reporteVenta) {
        return EntityModel.of(
            reporteVenta,
            linkTo(methodOn(ReporteVentaControllerV2.class).getReporteVentaById(reporteVenta.getIdVenta())).withSelfRel(),
            linkTo(methodOn(ReporteVentaControllerV2.class).getALL()).withRel("reportesVentas")
        );
    }


}