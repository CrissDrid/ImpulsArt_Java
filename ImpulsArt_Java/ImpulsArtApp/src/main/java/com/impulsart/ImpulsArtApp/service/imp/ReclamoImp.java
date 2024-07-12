package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Reclamo;
import com.impulsart.ImpulsArtApp.repositories.ReclamoRepository;
import com.impulsart.ImpulsArtApp.service.ReclamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReclamoImp implements ReclamoService {

    @Autowired
    ReclamoRepository reclamoRepository;

    @Override
    public List<Reclamo> findAll() throws Exception {
        return this.reclamoRepository.findAll();
    }

    @Override
    public Reclamo findById(Long pkCod_Reclamo) {
        return this.reclamoRepository.findById(pkCod_Reclamo).orElse(null);
    }

    @Override
    public void create(Reclamo reclamo) {
        this.reclamoRepository.save(reclamo);
    }

    @Override
    public void update(Reclamo reclamo) {
        this.reclamoRepository.save(reclamo);
    }

    @Override
    public void delete(Reclamo reclamo) {
        this.reclamoRepository.delete(reclamo);
    }

}
