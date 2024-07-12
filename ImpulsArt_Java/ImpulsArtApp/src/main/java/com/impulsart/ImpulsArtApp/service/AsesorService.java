package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Asesor;

import java.util.List;

public interface AsesorService {

    public List<Asesor> findAll() throws Exception;
    public Asesor findById(Long pkCod_Asesor);
    public void create (Asesor asesor);
    public void update (Asesor asesor);
    public void delete (Asesor asesor);

}
