package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Pqrs;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PqrsService {

    public List<Pqrs> findAll() throws Exception;
    List<Pqrs> findHistorialPqrs(@Param("identificacion") Long identificacion);
    List<Pqrs> findPqrsAsignadoAsesores(@Param("identificacion") Long identificacion);
    public Pqrs findById(Long pkCod_Reclamo);
    public void create (Pqrs pqrs);
    public void update (Pqrs pqrs);
    public void delete (Pqrs pqrs);

}
