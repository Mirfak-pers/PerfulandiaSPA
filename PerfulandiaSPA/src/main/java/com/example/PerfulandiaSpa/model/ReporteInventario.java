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
@Table(name = "reporte_inventario")
@Data
@NoArgsConstructor
public class ReporteInventario {

    @Id
    @Column(name = "id_reporte", unique = true, nullable = false) // Especificar columna con nombre exacto en la base de datos
    private String idReporte; // ID único para el reporte

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto; // Relación con la entidad Producto

    @Column(nullable = false)
    private int cantidadDisponible; // Cantidad disponible del producto en inventario

    @Column(nullable = false)
    private double precioCompra; // Precio de compra del producto

    @Column(nullable = false)
    private double precioVenta; // Precio de venta del producto

    @Column(nullable = false)
    private String estado; // Estado del producto en el inventario (disponible, agotado, etc.)


    public ReporteInventario(String idReporte, Producto producto, int cantidadDisponible, double precioCompra, double precioVenta, String estado) {
        setIdReporte(idReporte);
        setProducto(producto);
        setCantidadDisponible(cantidadDisponible);
        setPrecioCompra(precioCompra);
        setPrecioVenta(precioVenta);
        setEstado(estado);
    }
    public void setIdReporte(String idReporte) {
        if (idReporte == null || idReporte.trim().isEmpty()) {
            throw new IllegalArgumentException("El idReporte no puede estar vacío");
        }
        this.idReporte = idReporte;
    }

    public void setProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        this.producto = producto;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        if (cantidadDisponible < 0) {
            throw new IllegalArgumentException("La cantidad disponible no puede ser negativa");
        }
        this.cantidadDisponible = cantidadDisponible;
    }

    public void setPrecioCompra(double precioCompra) {
        if (precioCompra < 0) {
            throw new IllegalArgumentException("El precio de compra no puede ser negativo");
        }
        this.precioCompra = precioCompra;
    }

    public void setPrecioVenta(double precioVenta) {
        if (precioVenta < 0) {
            throw new IllegalArgumentException("El precio de venta no puede ser negativo");
        }
        this.precioVenta = precioVenta;
    }

    public void setEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }
        this.estado = estado;
    }
}
