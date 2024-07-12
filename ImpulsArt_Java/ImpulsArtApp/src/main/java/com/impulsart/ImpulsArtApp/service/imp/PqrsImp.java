package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Reclamo;
import com.impulsart.ImpulsArtApp.repositories.PqrsRepository;
import com.impulsart.ImpulsArtApp.service.PqrsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PqrsImp implements PqrsService {

    @Autowired
    PqrsRepository pqrsRepository;

    @Override
    public List<Reclamo> findAll() throws Exception {
        return this.pqrsRepository.findAll();
    }

    @Override
    public Reclamo findById(Long pkCod_PQRS) {
        return this.pqrsRepository.findById(pkCod_PQRS).orElse(null);
    }

    @Override
    public void create(Reclamo reclamo) {
        this.pqrsRepository.save(reclamo);
    }

    @Override
    public void update(Reclamo reclamo) {
        this.pqrsRepository.save(reclamo);
    }

    @Override
    public void delete(Reclamo reclamo) {
        this.pqrsRepository.delete(reclamo);
    }

}
