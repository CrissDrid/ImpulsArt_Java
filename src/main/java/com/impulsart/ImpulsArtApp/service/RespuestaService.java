package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Respuesta;

import java.util.List;

public interface RespuestaService {

    public List<Respuesta> findAll() throws Exception;
    public Respuesta findById(Long pkCod_Respuesta);
    public void create (Respuesta respuesta);
    public void update (Respuesta respuesta);
    public void delete (Respuesta respuesta);

}
