package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Reembolso;

import java.util.List;

public interface DevolucionService {

    public List<Reembolso> findAll() throws Exception;
    public Reembolso findById(Long pk_CodDevolucion);
    public void create (Reembolso reembolso);
    public void update (Reembolso reembolso);
    public void delete (Reembolso reembolso);

}
