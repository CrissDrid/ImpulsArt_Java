package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Calificacion;

import java.util.List;

public interface CalificacionService {

    public List<Calificacion> findAll() throws Exception;
    public Calificacion findById(Long pkCod_Calificacion);
    public void create (Calificacion calificacion);
    public void update (Calificacion calificacion);
    public void delete (Calificacion calificacion);

}
