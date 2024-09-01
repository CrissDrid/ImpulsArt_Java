package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Direccion;
import com.impulsart.ImpulsArtApp.repositories.DireccionRepository;
import com.impulsart.ImpulsArtApp.service.DireccionService;
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
