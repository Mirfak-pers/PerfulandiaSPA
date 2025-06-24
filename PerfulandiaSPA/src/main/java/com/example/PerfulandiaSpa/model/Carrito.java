package com.example.PerfulandiaSpa.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

@OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
private List<ItemCarrito> items = new ArrayList<>();

public void setUsuario(Usuario usuario) {
    if (usuario == null) {
        throw new IllegalArgumentException("El usuario no puede ser nulo");
    }
    this.usuario = usuario;
}

public void setItems(List<ItemCarrito> items) {
    if (items == null) {
        throw new IllegalArgumentException("La lista de items no puede ser nula");
    }
    this.items = items;
}
}