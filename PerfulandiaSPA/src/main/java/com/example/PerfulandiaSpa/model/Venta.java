package com.example.PerfulandiaSpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "venta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private String metodoPago;

    @ManyToMany
    @JoinTable(
        name = "venta_cupon",
        joinColumns = @JoinColumn(name = "venta_id"),
        inverseJoinColumns = @JoinColumn(name = "cupon_id")
    )
    private List<Cupon> cupones;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Venta(long id,LocalDateTime fecha, Double total, String metodoPago, List<Cupon> cupones, Usuario usuario) {
        this.setId(id);
        this.setFecha(fecha);
        this.setTotal(total);
        this.setMetodoPago(metodoPago);
        this.setCupones(cupones);
        this.setUsuario(usuario);
    }   

    public void setFecha(LocalDateTime fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        this.fecha = fecha;
    }

    public void setTotal(Double total) {
        if (total == null || total < 0) {
            throw new IllegalArgumentException("El total no puede ser nulo ni negativo");
        }
        this.total = total;
    }

    public void setMetodoPago(String metodoPago) {
        if (metodoPago == null || metodoPago.trim().isEmpty()) {
            throw new IllegalArgumentException("El método de pago no puede ser nulo o vacío");
        }
        this.metodoPago = metodoPago;
    }

    public void setCupones(List<Cupon> cupones) {
        this.cupones = cupones;
    }

    public void setUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        this.usuario = usuario;
    }
}
