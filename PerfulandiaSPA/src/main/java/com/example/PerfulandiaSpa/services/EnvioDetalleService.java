package com.example.PerfulandiaSpa.services;

import com.example.PerfulandiaSpa.model.EnvioDetalle;
import com.example.PerfulandiaSpa.repository.EnvioDetalleRepository;
import com.example.PerfulandiaSpa.repository.EnvioDetalleRepositoryJpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvioDetalleService {

    @Autowired
    private EnvioDetalleRepository envioDetalleRepository;
    private EnvioDetalleRepositoryJpa envioDetalleRepositoryJpa;

    public EnvioDetalle saveEnvioDetalle(EnvioDetalle detalle) {
        return envioDetalleRepository.guardar(detalle);
    }

    public List<EnvioDetalle> getAllEnvioDetalles() {
        return envioDetalleRepository.listar();
    }

    public void deleteEnvioDetalle(Long id) {
        envioDetalleRepository.buscarPorId(id);
    }

        public EnvioDetalle getEnvioDetalleById(Long id) {
        Optional<EnvioDetalle> envioDetalle = envioDetalleRepositoryJpa.findById(id);
        return envioDetalle.orElse(null);
    }
}