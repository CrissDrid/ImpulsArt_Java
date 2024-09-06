package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Categoria;

import java.util.List;

public interface CategoriaService {

    public List<Categoria> findAll() throws Exception;
    public Categoria findById(Long pkCod_Categoria);
    public void create (Categoria categoria);
    public void update (Categoria categoria);
    public void delete (Categoria categoria);

}
