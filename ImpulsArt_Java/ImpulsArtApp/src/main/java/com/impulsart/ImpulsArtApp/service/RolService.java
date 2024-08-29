package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Rol;

import java.util.List;

public interface RolService {

    public List<Rol> findAll() throws Exception;
    public Rol findById(Long pkCod_Rol);
    public List<Rol> findByNombre(String nombre);
    public void create (Rol rol);
    public void update (Rol rol);
    public void delete (Rol rol);

}
