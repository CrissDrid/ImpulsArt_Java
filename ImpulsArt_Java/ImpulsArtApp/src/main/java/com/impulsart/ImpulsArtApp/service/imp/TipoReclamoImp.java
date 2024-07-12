package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.TipoReclamo;
import com.impulsart.ImpulsArtApp.repositories.TipoReclamoRepository;
import com.impulsart.ImpulsArtApp.service.TipoReclamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoReclamoImp implements TipoReclamoService {

    @Autowired
    TipoReclamoRepository tipoReclamoRepository;

    @Override
    public List<TipoReclamo> findAll() throws Exception {
        return this.tipoReclamoRepository.findAll();
    }

    @Override
    public TipoReclamo findById(Long pkCod_TipoReclamo) {
        return this.tipoReclamoRepository.findById(pkCod_TipoReclamo).orElse(null);
    }

    @Override
    public void create(TipoReclamo tipoReclamo) {
        this.tipoReclamoRepository.save(tipoReclamo);
    }

    @Override
    public void update(TipoReclamo tipoReclamo) {
        this.tipoReclamoRepository.save(tipoReclamo);
    }

    @Override
    public void delete(TipoReclamo tipoReclamo) {
        this.tipoReclamoRepository.delete(tipoReclamo);
    }

}
