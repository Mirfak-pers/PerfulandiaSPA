package com.example.PerfulandiaSpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Pedido")
@Data
@AllArgsConstructor
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
}
