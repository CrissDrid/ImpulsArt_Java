package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.RegistroDespacho;
import com.impulsart.ImpulsArtApp.entities.Vehiculo;

import java.util.List;

public interface VehiculoService {

    public List<Vehiculo> findAll() throws Exception;
    public Vehiculo findById(Long pk_placa);
    public void create (Vehiculo vehiculo);
    public void update (Vehiculo vehiculo);
    public void delete (Vehiculo vehiculo);

}
