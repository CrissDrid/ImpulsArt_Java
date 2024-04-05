package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Oferta;
import com.impulsart.ImpulsArtApp.entities.Subasta;

import java.time.LocalDate;
import java.util.List;

public interface SubastaService {

    public List<Subasta> findAll() throws Exception;
    Subasta findById(Long pkCodSubasta);
    public void create(Subasta subasta);
    public void delete(Subasta subasta);
    public void update(Subasta subasta);

    List<Subasta> findByEstadoSubastaContainingIgnoreCase(String estadoSubasta);

}
