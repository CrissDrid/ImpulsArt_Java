package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.TipoReclamo;
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
    public List<TipoReclamo> findAll() throws Exception {
        return this.tipoPQRSRepository.findAll();
    }

    @Override
    public TipoReclamo findById(Long pkCod_TipoPQRS) {
        return this.tipoPQRSRepository.findById(pkCod_TipoPQRS).orElse(null);
    }

    @Override
    public void create(TipoReclamo tipoReclamo) {
        this.tipoPQRSRepository.save(tipoReclamo);
    }

    @Override
    public void update(TipoReclamo tipoReclamo) {
        this.tipoPQRSRepository.save(tipoReclamo);
    }

    @Override
    public void delete(TipoReclamo tipoReclamo) {
        this.tipoPQRSRepository.delete(tipoReclamo);
    }

}
