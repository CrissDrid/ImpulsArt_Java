package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Resena;

import java.util.List;

public interface ResenaService {
    public List<Resena> findAll() throws Exception;
    public Resena findById(Long pkCod_Resena);
    public void create (Resena resena);
    public void update (Resena resena);
    public void delete (Resena resena);
}
