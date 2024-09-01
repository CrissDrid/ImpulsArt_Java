package com.impulsart.ImpulsArtApp.service.imp;


import com.impulsart.ImpulsArtApp.entities.Resena;
import com.impulsart.ImpulsArtApp.repositories.ResenaRepository;
import com.impulsart.ImpulsArtApp.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResenaImp implements ResenaService {
    @Autowired
    private ResenaRepository resenaRepository;

    @Override
    public List<Resena> findAll() throws Exception {
        return this.resenaRepository.findAll();
    }

    @Override
    public Resena findById(Long pkCod_Resena) {
        return this.resenaRepository.findById(pkCod_Resena).orElse(null);
    }

    @Override
    public void create(Resena resena) {
        this.resenaRepository.save(resena);
    }

    @Override
    public void update(Resena resena) {
        this.resenaRepository.save(resena);
    }

    @Override
    public void delete(Resena resena) {
        this.resenaRepository.delete(resena);
    }
}
