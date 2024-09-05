package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Oferta;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfertaService {

    List<Oferta> findOfertasBySubasta(@Param("pkCodSubasta") Long pkCodSubasta);
    Oferta findOfertaMasAlta(@Param("pkCodSubasta") Long pkCodSubasta);
    Oferta findBySubastaIdAndUsuarioId(Long subastaId, int usuarioId);
    public List<Oferta> findAll() throws Exception;
    Oferta findById(Long PkCod_oferta);
    public void create(Oferta oferta);
    public void delete(Oferta oferta);
    public void update(Oferta oferta);

}
