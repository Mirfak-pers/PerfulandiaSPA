package com.example.PerfulandiaSpa.asembled;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.PerfulandiaSpa.controller_HATEOAS.ReporteRendimientoControllerV2;
import com.example.PerfulandiaSpa.model.ReporteRendimiento;


@Component
public class reporteRendimientoModelAssembled implements RepresentationModelAssembler<ReporteRendimiento, EntityModel<ReporteRendimiento>> {

    @Override
    public EntityModel<ReporteRendimiento> toModel(ReporteRendimiento reporteRendimiento) {
        return EntityModel.of(
            reporteRendimiento,
            linkTo(methodOn(ReporteRendimientoControllerV2.class).getReporteRendimientoById(reporteRendimiento.getSucursal().getId())).withSelfRel(),
            linkTo(methodOn(ReporteRendimientoControllerV2.class).getALL()).withRel("reportesRendimiento")
        );
    }


}