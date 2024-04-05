package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Pqrs;
import com.impulsart.ImpulsArtApp.entities.TipoPQRS;
import com.impulsart.ImpulsArtApp.repositories.PqrsRepository;
import com.impulsart.ImpulsArtApp.repositories.TipoPQRSRepository;
import com.impulsart.ImpulsArtApp.service.TipoPQRSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoPQRSImp implements TipoPQRSService {

    @Autowired
    TipoPQRSRepository tipoPQRSRepository;

    @Override
    public List<TipoPQRS> findAll() throws Exception {
        return this.tipoPQRSRepository.findAll();
    }

    @Override
    public TipoPQRS findById(Long pkCod_TipoPQRS) {
        return this.tipoPQRSRepository.findById(pkCod_TipoPQRS).orElse(null);
    }

    @Override
    public void create(TipoPQRS tipoPQRS) {
        this.tipoPQRSRepository.save(tipoPQRS);
    }

    @Override
    public void update(TipoPQRS tipoPQRS) {
        this.tipoPQRSRepository.save(tipoPQRS);
    }

    @Override
    public void delete(TipoPQRS tipoPQRS) {
        this.tipoPQRSRepository.delete(tipoPQRS);
    }

}
