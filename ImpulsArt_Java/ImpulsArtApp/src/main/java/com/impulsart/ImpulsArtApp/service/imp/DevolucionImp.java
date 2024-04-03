package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Devolucion;
import com.impulsart.ImpulsArtApp.repositories.DevolucionRepository;
import com.impulsart.ImpulsArtApp.service.DevolucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevolucionImp implements DevolucionService {


    @Autowired
    private DevolucionRepository devolucionRepository;

    @Override
    public List<Devolucion> findAll() throws Exception {
        return this.devolucionRepository.findAll();
    }

    @Override
    public Devolucion findById(Long pk_CodDevolucion) {
        return this.devolucionRepository.findById(pk_CodDevolucion).orElse(null);
    }

    @Override
    public void create(Devolucion devolucion) {
        this.devolucionRepository.save(devolucion);
    }

    @Override
    public void update(Devolucion devolucion) {
        this.devolucionRepository.save(devolucion);
    }

    @Override
    public void delete(Devolucion devolucion) {
        this.devolucionRepository.delete(devolucion);
    }
}
