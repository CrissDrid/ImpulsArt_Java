package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Pqrs;
import com.impulsart.ImpulsArtApp.repositories.PqrsRepository;
import com.impulsart.ImpulsArtApp.service.PqrsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PqrsImp implements PqrsService {

    @Autowired
    PqrsRepository pqrsRepository;

    @Override
    public List<Pqrs> findAll() throws Exception {
        return this.pqrsRepository.findAll();
    }

    @Override
    public List<Pqrs> findHistorialPqrs(Integer identificacion) {
        List<Pqrs> pqrs = this.pqrsRepository.findHistorialPqrs(identificacion);
        if (pqrs.isEmpty()) {
            throw new EntityNotFoundException("Reporte de las obras no encontradas");
        }
        return pqrs;
    }

    @Override
    public Pqrs findById(Long pkCod_Pqrs) {
        return this.pqrsRepository.findById(pkCod_Pqrs).orElse(null);
    }

    @Override
    public void create(Pqrs pqrs) {
        this.pqrsRepository.save(pqrs);
    }

    @Override
    public void update(Pqrs pqrs) {
        this.pqrsRepository.save(pqrs);
    }

    @Override
    public void delete(Pqrs pqrs) {
        this.pqrsRepository.delete(pqrs);
    }

}
