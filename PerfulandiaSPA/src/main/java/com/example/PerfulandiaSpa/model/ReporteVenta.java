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
@Table(name = "reporte_ventas")
@Data
@NoArgsConstructor
public class ReporteVenta {

    @Id
    private String idVenta;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private int cantidadVendida;

    @Column(nullable = false)
    private int precioUnitario;

    @Column(nullable = false)
    private double totalVenta;

    @Column(nullable = false)
    private String fechaVenta;

    @Column(nullable = false)
    private String metodoPago;

    @Column(nullable = false)
    private String rutCliente;

    public ReporteVenta(String idVenta, Producto producto, int cantidadVendida, int precioUnitario, double totalVenta, String fechaVenta, String metodoPago, String rutCliente) {
        this.idVenta = idVenta;
        this.setProducto(producto);
        this.setCantidadVendida(cantidadVendida);
        this.setPrecioUnitario(precioUnitario);
        this.setTotalVenta(totalVenta);
        this.setFechaVenta(fechaVenta);
        this.setMetodoPago(metodoPago);
        this.setRutCliente(rutCliente);
    }

    public void setCantidadVendida(int cantidadVendida) {
        if (cantidadVendida <= 0) {
            throw new IllegalArgumentException("La cantidad vendida debe ser mayor que cero.");
        }
        this.cantidadVendida = cantidadVendida;
    }

    public void setPrecioUnitario(int precioUnitario) {
        if (precioUnitario < 0) {
            throw new IllegalArgumentException("El precio unitario no puede ser negativo.");
        }
        this.precioUnitario = precioUnitario;
    }

    public void setTotalVenta(double totalVenta) {
        if (totalVenta < 0) {
            throw new IllegalArgumentException("El total de la venta no puede ser negativo.");
        }
        this.totalVenta = totalVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        if (fechaVenta == null || fechaVenta.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de venta no puede estar vacía.");
        }
        this.fechaVenta = fechaVenta;
    }

    public void setMetodoPago(String metodoPago) {
        if (metodoPago == null || metodoPago.trim().isEmpty()) {
            throw new IllegalArgumentException("El método de pago no puede estar vacío.");
        }
        this.metodoPago = metodoPago;
    }

    public void setRutCliente(String rutCliente) {
        if (rutCliente == null || rutCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("El RUT del cliente no puede estar vacío.");
        }
        this.rutCliente = rutCliente;
    }
}