package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.TipoPqrs;
import com.impulsart.ImpulsArtApp.repositories.TipoPqrsRepository;
import com.impulsart.ImpulsArtApp.service.TipoPqrsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoPqrsImp implements TipoPqrsService {

    @Autowired
    TipoPqrsRepository tipoPqrsRepository;

    @Override
    public List<TipoPqrs> findAll() throws Exception {
        return this.tipoPqrsRepository.findAll();
    }

    @Override
    public TipoPqrs findById(Long pkCod_TipoPqrs) {
        return this.tipoPqrsRepository.findById(pkCod_TipoPqrs).orElse(null);
    }

    @Override
    public void create(TipoPqrs tipoPQRS) {
        this.tipoPqrsRepository.save(tipoPQRS);
    }

    @Override
    public void update(TipoPqrs tipoPQRS) {
        this.tipoPqrsRepository.save(tipoPQRS);
    }

    @Override
    public void delete(TipoPqrs tipoPQRS) {
        this.tipoPqrsRepository.delete(tipoPQRS);
    }

}
