package com.example.PerfulandiaSpa.model;

import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Envios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Envio {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "origen_id")
    private Sucursal origen;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destino_id")
    private Sucursal destino;

    @OneToMany(mappedBy = "envio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnvioDetalle> detalles;

  
    @ManyToOne
    @JoinColumn(name = "estado_envio_id")
    private EstadoEnvio estado;

    @Column(nullable = false)
    private LocalDateTime fechaEnvio;

    private LocalDateTime fechaRecepcion;

    public Envio(Sucursal origen, Sucursal destino, List<EnvioDetalle> detalles, EstadoEnvio estado, LocalDateTime fechaEnvio) {
      this.setOrigen(origen);
      this.setDestino(destino);
      this.setDetalles(detalles);
      this.setEstado(estado);
      this.setFechaEnvio(fechaEnvio);
    }

    public void setOrigen(Sucursal origen) {
      if (origen == null) {
        throw new IllegalArgumentException("El origen no puede ser nulo");
      }
      this.origen = origen;
    }

    public void setDestino(Sucursal destino) {
      if (destino == null) {
        throw new IllegalArgumentException("El destino no puede ser nulo");
      }
      this.destino = destino;
    }

    public void setDetalles(List<EnvioDetalle> detalles) {
      if (detalles == null || detalles.isEmpty()) {
        throw new IllegalArgumentException("La lista de detalles no puede estar vacía");
      }
      this.detalles = detalles;
    }

    public void setEstado(EstadoEnvio estado) {
      if (estado == null) {
        throw new IllegalArgumentException("El estado no puede ser nulo");
      }
      this.estado = estado;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
      if (fechaEnvio == null) {
        throw new IllegalArgumentException("La fecha de envío no puede ser nula");
      }
      this.fechaEnvio = fechaEnvio;
    }
}