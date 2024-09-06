package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.TipoPqrs;

import java.util.List;

public interface TipoPqrsService {

    public List<TipoPqrs> findAll() throws Exception;
    public TipoPqrs findById(Long pkCod_TipoReclamo);
    public void create (TipoPqrs tipoPQRS);
    public void update (TipoPqrs tipoPQRS);
    public void delete (TipoPqrs tipoPQRS);

}
