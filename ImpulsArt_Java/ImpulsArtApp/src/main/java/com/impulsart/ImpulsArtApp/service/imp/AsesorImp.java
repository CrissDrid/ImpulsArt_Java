package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Asesor;
import com.impulsart.ImpulsArtApp.repositories.AsesorRepository;
import com.impulsart.ImpulsArtApp.service.AsesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsesorImp implements AsesorService {

    @Autowired
    private AsesorRepository asesorRepository;

    @Override
    public List<Asesor> findAll() throws Exception {
        return this.asesorRepository.findAll();
    }

    @Override
    public Asesor findById(Long pkCod_Asesor) {
        return this.asesorRepository.findById(pkCod_Asesor).orElse(null);
    }

    @Override
    public void create(Asesor asesor) {
        this.asesorRepository.save(asesor);

    }

    @Override
    public void update(Asesor asesor) {
        this.asesorRepository.save(asesor);
    }

    @Override
    public void delete(Asesor asesor) {
        this.asesorRepository.delete(asesor);
    }
}
