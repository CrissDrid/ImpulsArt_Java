package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Direccion;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DireccionService {
    public List<Direccion> findAll() throws Exception;
    List<Direccion> findHistorialDireccion(@Param("identificacion") Long identificacion);
    public boolean existsByCiudadAndDireccionAndUsuario(String ciudad, String direccion, Usuario usuario);
    public Direccion findById(Long id);
    public void create(Direccion direccion);
    public void update(Direccion direccion);
    public void delete(Direccion direccion);
}
