package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Domiciliario;

import java.util.List;

public interface DomiciliarioService {

    public List<Domiciliario> findAll() throws Exception;
    public Domiciliario findById(Long pkCod_Domiciliario);
    public void create (Domiciliario domiciliario);
    public void update (Domiciliario domiciliario);
    public void delete (Domiciliario domiciliario);

}
