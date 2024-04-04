package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Empleado;
import com.impulsart.ImpulsArtApp.entities.Pqrs;
import com.impulsart.ImpulsArtApp.repositories.EmpleadoRepository;
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
    public List<Pqrs> findAll() throws Exception {
        return this.pqrsRepository.findAll();
    }

    @Override
    public Pqrs findById(Long pkCod_PQRS) {
        return this.pqrsRepository.findById(pkCod_PQRS).orElse(null);
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
