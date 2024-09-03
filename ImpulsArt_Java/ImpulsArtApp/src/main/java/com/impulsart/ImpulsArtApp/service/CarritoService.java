package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Carrito;

import java.util.List;

public interface CarritoService {

    List<Carrito> findAll() throws Exception;

    Carrito findById(Long PkCod_Carrito) throws Exception;

    void create(Carrito carrito) throws Exception;

    void update(Carrito carrito) throws Exception;

    void delete(Carrito carrito) throws Exception;
}

