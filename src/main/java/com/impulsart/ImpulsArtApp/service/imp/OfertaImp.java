package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Oferta;
import com.impulsart.ImpulsArtApp.entities.Subasta;
import com.impulsart.ImpulsArtApp.entities.Usuario;
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

    @Autowired
    private UsuarioImp usuarioImp;

    @Override
    public List<Oferta> findOfertasBySubasta(Long pkCodSubasta) {
        List<Oferta> ofertas = this.ofertaRepository.findOfertasBySubasta(pkCodSubasta);
        return ofertas;  // Devuelve la lista vacía en lugar de lanzar una excepción
    }

    @Override
    public Oferta findOfertaMasAlta(Long pkCodSubasta) {
        return this.ofertaRepository.findOfertaMasAlta(pkCodSubasta);  // Devuelve null si no se encuentra
    }

    @Override
    public Oferta findBySubastaIdAndUsuarioId(Long subastaId, Long usuarioId) {

        // Buscar oferta existente
        return ofertaRepository.findBySubastaIdAndUsuarioId(subastaId, usuarioId);

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
