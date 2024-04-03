package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Pqrs;
import com.impulsart.ImpulsArtApp.entities.TipoPQRS;

import java.util.List;

public interface TipoPQRSService {

    public List<TipoPQRS> findAll() throws Exception;
    public TipoPQRS findById(Long pkCod_TipoPQRS);
    public void create (TipoPQRS tipoPQRS);
    public void update (TipoPQRS tipoPQRS);
    public void delete (TipoPQRS tipoPQRS);

}
