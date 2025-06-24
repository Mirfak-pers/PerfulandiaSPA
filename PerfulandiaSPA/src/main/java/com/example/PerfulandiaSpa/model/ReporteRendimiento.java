package com.example.PerfulandiaSpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="reporte_rendimiento")
@Data
@NoArgsConstructor
public class ReporteRendimiento {
    @Id
    long id;
    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;      
    @Column(nullable = false)
    private String nombreSucursal;
    @Column(nullable = false)   
    private double ventasTotales;
    @Column(nullable = false)     
    private double metasCumplidas;    
    @Column(nullable = false)
    private double ganancias; 
    @Column(nullable = false)        
    private int empleados;   
    @Column(nullable = false)          
    private String periodo;   

    public ReporteRendimiento(long id, Sucursal sucursal, String nombreSucursal, double ventasTotales, double metasCumplidas, double ganancias, int empleados, String periodo) {
        this.id = id;
        this.setSucursal(sucursal);
        this.setNombreSucursal(nombreSucursal);
        this.setVentasTotales(ventasTotales);
        this.setMetasCumplidas(metasCumplidas);
        this.setGanancias(ganancias);
        this.setEmpleados(empleados);
        this.setPeriodo(periodo);
    }


    public void setVentasTotales(double ventasTotales) {
        if (ventasTotales < 0) {
            throw new IllegalArgumentException("Las ventas totales no pueden ser negativas.");
        }
        this.ventasTotales = ventasTotales;
    }

    public void setMetasCumplidas(double metasCumplidas) {
        if (metasCumplidas < 0) {
            throw new IllegalArgumentException("Las metas cumplidas no pueden ser negativas.");
        }
        this.metasCumplidas = metasCumplidas;
    }

    public void setGanancias(double ganancias) {
        if (ganancias < 0) {
            throw new IllegalArgumentException("Las ganancias no pueden ser negativas.");
        }
        this.ganancias = ganancias;
    }

    public void setEmpleados(int empleados) {
        if (empleados < 0) {
            throw new IllegalArgumentException("El número de empleados no puede ser negativo.");
        }
        this.empleados = empleados;
    }

    public void setNombreSucursal(String nombreSucursal) {
        if (nombreSucursal == null || nombreSucursal.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la sucursal no puede estar vacío.");
        }
        this.nombreSucursal = nombreSucursal;
    }

    public void setPeriodo(String periodo) {
        if (periodo == null || periodo.trim().isEmpty()) {
            throw new IllegalArgumentException("El periodo no puede estar vacío.");
        }
        this.periodo = periodo;
    }
}
