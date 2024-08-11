package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Obra;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ObraService {
    public List<Obra> findAll() throws Exception;
    List<Obra> findHistorialObras(@Param("identificacion") Integer identificacion);
    List<Obra> findByNombreProductoContainingIgnoreCase(String nombreProducto);
    List<Obra> findObrasSinSubasta();
    public Obra findById(Integer pkCod_Producto);
    public void create (Obra obra);
    public void update (Obra obra);
    public void delete (Obra obra);
}
