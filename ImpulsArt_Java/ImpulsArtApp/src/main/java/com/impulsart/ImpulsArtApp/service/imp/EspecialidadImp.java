package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Especialidad;
import com.impulsart.ImpulsArtApp.repositories.EspecialidadRepositorio;
import com.impulsart.ImpulsArtApp.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EspecialidadImp implements EspecialidadService {
    @Autowired
    private EspecialidadRepositorio especialidadRepositorio;

    @Override
    public List<Especialidad> findAll() throws Exception {
        return this.especialidadRepositorio.findAll();
    }

    @Override
    public Especialidad fidnById(Integer Pk_Especialidad) {
        return this.especialidadRepositorio.findById(Pk_Especialidad).orElse(null);
    }

    @Override
    public void create(Especialidad especialidad) {
        this.especialidadRepositorio.save(especialidad);
    }

    @Override
    public void update(Especialidad especialidad) {
        this.especialidadRepositorio.save(especialidad);
    }

    @Override
    public void delete(Especialidad especialidad) {
        this.especialidadRepositorio.delete(especialidad);
    }
}
