package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Direccion;
import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.repositories.DireccionRepository;
import com.impulsart.ImpulsArtApp.service.DireccionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionImp implements DireccionService {

    @Autowired
    private DireccionRepository direccionRepositorio;

    @Override
    public List<Direccion> findAll() throws Exception {
        return this.direccionRepositorio.findAll();
    }

    @Override
    public List<Direccion> findHistorialDireccion(Integer identificacion) {
        List<Direccion> direcciones = this.direccionRepositorio.findHistorialDireccion(identificacion);
        if (direcciones.isEmpty()) {
            throw new EntityNotFoundException("Direcciones no encontradas");
        }
        return direcciones;
    }

    @Override
    public boolean existsByCiudadAndDireccionAndUsuario(String ciudad, String direccion, Usuario usuario) {
        return direccionRepositorio.existsByCiudadAndDireccionAndUsuario(ciudad, direccion, usuario);
    }

    @Override
    public Direccion findById(Long id) {
        return this.direccionRepositorio.findById(id).orElse(null);
    }

    @Override
    public void create(Direccion direccion) {
        this.direccionRepositorio.save(direccion);
    }

    @Override
    public void update(Direccion direccion) {
        this.direccionRepositorio.save(direccion);
    }

    @Override
    public void delete(Direccion direccion) {
        this.direccionRepositorio.delete(direccion);
    }
}
