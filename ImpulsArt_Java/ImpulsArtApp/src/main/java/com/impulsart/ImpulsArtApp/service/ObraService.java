package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Obra;

import java.util.List;

public interface ObraService {
    public List<Obra> findAll() throws Exception;
    public Obra findById(Integer PkCod_Producto);
    public void create (Obra obra);
    public void update (Obra obra);
    public void delete (Obra obra);
}
