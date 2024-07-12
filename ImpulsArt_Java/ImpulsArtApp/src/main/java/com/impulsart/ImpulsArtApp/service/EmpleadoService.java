package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Empleado;

import java.util.List;

public interface EmpleadoService {

    public List<Empleado> findAll() throws Exception;
    public Empleado findById(Long pkCod_Empleado);
    public void create (Empleado empleado);
    public void update (Empleado empleado);
    public void delete (Empleado empleado);

}
