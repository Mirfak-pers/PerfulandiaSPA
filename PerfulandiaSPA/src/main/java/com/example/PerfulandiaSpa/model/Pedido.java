package com.example.PerfulandiaSpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Pedido")
@Data
@NoArgsConstructor
public class Pedido {

    @Id
    private int id;

    // Relación ManyToMany entre Pedido y Producto
    @ManyToMany
    @JoinTable(
        name = "pedido_producto", 
        joinColumns = @JoinColumn(name = "id_pedido"), 
        inverseJoinColumns = @JoinColumn(name = "id_producto")
    )
    private List<Producto> productos;

    @Column(nullable = false)
    private Date fechaCreacion;

    @Column(nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;


    public Pedido(int id, List<Producto> productos, Date fechaCreacion, String estado, Usuario usuario, Sucursal sucursal) {
        this.id = id;
        this.productos = productos;
        this.fechaCreacion = fechaCreacion;
        setEstado(estado);
        this.usuario = usuario;
        this.sucursal = sucursal;
    }
    public void setEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }
        this.estado = estado;
    }
}
