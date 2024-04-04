package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Venta;

import java.util.List;

public interface VentaService {
    public List<Venta> findAll() throws Exception;
    public Venta findById(Integer PkCod_Venta);
    public void create(Venta venta);
    public void update(Venta venta);
    public void delete(Venta venta);
}
