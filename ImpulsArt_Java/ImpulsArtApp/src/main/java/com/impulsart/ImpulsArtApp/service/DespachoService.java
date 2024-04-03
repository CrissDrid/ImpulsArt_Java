package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Despacho;
import com.impulsart.ImpulsArtApp.entities.Domiciliario;

import java.util.List;

public interface DespachoService {

    public List<Despacho> findAll() throws Exception;
    public Despacho findById(Long pkCod_Despacho);
    public void create (Despacho despacho);
    public void update (Despacho despacho);
    public void delete (Despacho despacho);

}
