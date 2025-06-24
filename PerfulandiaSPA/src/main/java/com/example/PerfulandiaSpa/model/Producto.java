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
@Table(name = "producto")
@Data
@NoArgsConstructor


public class Producto {

    @Id
    public int id_producto;
    @Column(nullable= false,length = 50)
    public String nombre;
    @Column(nullable= false,length=50)
    public int precio;
    @Column(nullable=false,length=50)
    public int stock_total;
    @Column(nullable=false,length=50)
    public String categoria;
    @Column(nullable=false,length=50)
    public String estado;
    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Provedor proveedor;


    public Producto(int id_producto, String nombre, int precio, int stock_total, String categoria, String estado, Provedor proveedor) {
        this.id_producto = id_producto;
        this.setNombre(nombre);
        this.setPrecio(precio);
        this.setStock_total(stock_total);
        this.setCategoria(categoria);
        this.setEstado(estado);
        this.proveedor = proveedor;
    }


    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public void setPrecio(int precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.precio = precio;
    }

    public void setStock_total(int stock_total) {
        if (stock_total < 0) {
            throw new IllegalArgumentException("El stock total no puede ser negativo");
        }
        this.stock_total = stock_total;
    }

    public void setCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría no puede estar vacía");
        }
        this.categoria = categoria;
    }

    public void setEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }
        this.estado = estado;
    }
}
