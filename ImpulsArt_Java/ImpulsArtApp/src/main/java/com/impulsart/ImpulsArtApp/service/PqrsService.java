package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Empleado;
import com.impulsart.ImpulsArtApp.entities.Pqrs;

import java.util.List;

public interface PqrsService {

    public List<Pqrs> findAll() throws Exception;
    public Pqrs findById(Long pkCod_PQRS);
    public void create (Pqrs pqrs);
    public void update (Pqrs pqrs);
    public void delete (Pqrs pqrs);

}
