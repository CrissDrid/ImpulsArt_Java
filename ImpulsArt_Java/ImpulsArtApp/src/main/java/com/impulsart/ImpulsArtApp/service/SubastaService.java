package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Oferta;
import com.impulsart.ImpulsArtApp.entities.Subasta;

import java.util.List;

public interface SubastaService {

    public List<Subasta> findAll() throws Exception;
    public void create(Subasta subasta);
    public void delete(Subasta subasta);
    public void update(Subasta subasta);

}
