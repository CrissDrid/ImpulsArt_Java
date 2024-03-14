package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Oferta;

import java.util.List;

public interface OfertaService {

    public List<Oferta> findAll() throws Exception;
    Oferta findById(Long PkCod_oferta);
    public void create(Oferta oferta);
    public void delete(Oferta oferta);
    public void update(Oferta oferta);

}
