package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Especialidad;

import java.util.List;

public interface EspecialidadService {
    public List<Especialidad> findAll() throws Exception;
    public Especialidad fidnById(Integer Pk_Especialidad);
    public void create (Especialidad especialidad);
    public void update (Especialidad especialidad);
    public void delete (Especialidad especialidad);
}
