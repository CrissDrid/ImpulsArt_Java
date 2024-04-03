package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Devolucion;

import java.util.List;

public interface DevolucionService {

    public List<Devolucion> findAll() throws Exception;
    public Devolucion findById(Long pk_CodDevolucion);
    public void create (Devolucion devolucion);
    public void update (Devolucion devolucion);
    public void delete (Devolucion devolucion);

}
