package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Direccion;

import java.util.List;

public interface DireccionService {
    public List<Direccion> findAll() throws Exception;
    public Direccion findById(Long id);
    public void create(Direccion direccion);
    public void update(Direccion direccion);
    public void delete(Direccion direccion);
}
