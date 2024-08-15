package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Reclamo;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReclamoService {

    public List<Reclamo> findAll() throws Exception;
    List<Reclamo> findHistorialReclamos(@Param("identificacion") Integer identificacion);
    public Reclamo findById(Long pkCod_Reclamo);
    public void create (Reclamo reclamo);
    public void update (Reclamo reclamo);
    public void delete (Reclamo reclamo);

}
