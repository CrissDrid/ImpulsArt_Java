package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.TipoReclamo;

import java.util.List;

public interface TipoReclamoService {

    public List<TipoReclamo> findAll() throws Exception;
    public TipoReclamo findById(Long pkCod_TipoReclamo);
    public void create (TipoReclamo tipoReclamo);
    public void update (TipoReclamo tipoReclamo);
    public void delete (TipoReclamo tipoReclamo);

}
