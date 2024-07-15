package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Calificacion;
import com.impulsart.ImpulsArtApp.repositories.CalificacionRepository;
import com.impulsart.ImpulsArtApp.service.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalificacionImp implements CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Override
    public List<Calificacion> findAll() throws Exception {
        return this.calificacionRepository.findAll();
    }

    @Override
    public Calificacion findById(Long pkCod_Calificacion) {
        return this.calificacionRepository.findById(pkCod_Calificacion).orElse(null);
    }

    @Override
    public void create(Calificacion calificacion) {
        this.calificacionRepository.save(calificacion);
    }

    @Override
    public void update(Calificacion calificacion) {
        this.calificacionRepository.save(calificacion);
    }

    @Override
    public void delete(Calificacion calificacion) {
        this.calificacionRepository.delete(calificacion);
    }
}
