package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.RegistroDespacho;

import java.util.List;

public interface RegistroDespachoService {

    public List<RegistroDespacho> findAll() throws Exception;
    public RegistroDespacho findById(Long pkCod_registro);
    public void create (RegistroDespacho registroDespacho);
    public void update (RegistroDespacho registroDespacho);
    public void delete (RegistroDespacho registroDespacho);

}
