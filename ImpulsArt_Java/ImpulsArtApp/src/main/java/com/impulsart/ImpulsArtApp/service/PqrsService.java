package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Reclamo;

import java.util.List;

public interface PqrsService {

    public List<Reclamo> findAll() throws Exception;
    public Reclamo findById(Long pkCod_PQRS);
    public void create (Reclamo reclamo);
    public void update (Reclamo reclamo);
    public void delete (Reclamo reclamo);

}
