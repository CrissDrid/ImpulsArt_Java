package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Oferta;
import com.impulsart.ImpulsArtApp.entities.Subasta;
import com.impulsart.ImpulsArtApp.repositories.OfertaRepository;
import com.impulsart.ImpulsArtApp.service.OfertaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service 

public class OfertaImp implements OfertaService {
    @Autowired
    private OfertaRepository ofertaRepository;

    @Override
    public List<Oferta> findOfertasBySubasta(Long pkCodSubasta) {
        List<Oferta> ofertas = this.ofertaRepository.findOfertasBySubasta(pkCodSubasta);
        if (ofertas.isEmpty()) {
            throw new EntityNotFoundException("Oferta no encontrada");
        }
        return ofertas;
    }

    @Override
    public Oferta findOfertaMasAlta(Long pkCodSubasta) {
        Oferta oferta = this.ofertaRepository.findOfertaMasAlta(pkCodSubasta);
        if (oferta == null) {
            throw new EntityNotFoundException("La oferta más alta no fue encontrada");
        }
        return oferta;
    }

    @Override
    public List<Oferta> findAll() throws Exception {
        return this.ofertaRepository.findAll();
    }

    @Override
    public Oferta findById(Long PkCod_oferta) {
        return this.ofertaRepository.findById(PkCod_oferta).orElse(null);
    }

    @Override
    public void create(Oferta oferta) {
        this.ofertaRepository.save(oferta);
    }

    @Override
    public void delete(Oferta oferta) {
        this.ofertaRepository.delete(oferta);
    }

    @Override
    public void update(Oferta oferta) {
        this.ofertaRepository.save(oferta);
    }
}
