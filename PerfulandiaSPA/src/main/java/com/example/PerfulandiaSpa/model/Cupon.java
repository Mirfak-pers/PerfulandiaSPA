package com.example.PerfulandiaSpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cupon")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo_cupon;

    @Column(nullable = false)
    private double descuento; // porcentaje o monto fijo

    @Column(nullable = false)
    private boolean activo;
    public Cupon(String codigo_cupon, double descuento, boolean activo) {
        this.setCodigo_cupon(codigo_cupon);
        this.setDescuento(descuento);
        this.setActivo(activo);
    }

    public void setCodigo_cupon(String codigo_cupon) {
        if (codigo_cupon == null || codigo_cupon.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del cupón no puede estar vacío");
        }
        this.codigo_cupon = codigo_cupon;
    }

    public void setDescuento(double descuento) {
        if (descuento <= 0) {
            throw new IllegalArgumentException("El descuento debe ser mayor a 0");
        }
        this.descuento = descuento;
    }
}