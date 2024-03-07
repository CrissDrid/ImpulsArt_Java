package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Oferta;
import com.impulsart.ImpulsArtApp.repository.OfertaRepository;
import com.impulsart.ImpulsArtApp.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service 

public class OfertaImp implements OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    @Override
    public List<Oferta> findAll() throws Exception {
        return this.ofertaRepository.findAll();
    }

    @Override
    public void create(Oferta oferta) {
        this.ofertaRepository.save(oferta);
    }

    @Override
    public void delete(Oferta oferta) {
        this.ofertaRepository.save(oferta);
    }

    @Override
    public void update(Oferta oferta) {
        this.ofertaRepository.save(oferta);
    }
}
