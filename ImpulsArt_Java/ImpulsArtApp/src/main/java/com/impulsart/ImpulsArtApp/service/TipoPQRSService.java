package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.TipoReclamo;

import java.util.List;

public interface TipoPQRSService {

    public List<TipoReclamo> findAll() throws Exception;
    public TipoReclamo findById(Long pkCod_TipoPQRS);
    public void create (TipoReclamo tipoReclamo);
    public void update (TipoReclamo tipoReclamo);
    public void delete (TipoReclamo tipoReclamo);

}
