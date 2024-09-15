package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ObraService {
    public List<Obra> findAll() throws Exception;
    List<Obra>findSubastaAndObras();
    List<Obra> findHistorialObras(@Param("identificacion") Integer identificacion);
    List<Obra> findByNombreProductoContainingIgnoreCase(String nombreProducto);
    Usuario findCreadorByObraId(@Param("obraId") Integer obraId);
    List<Obra> findObrasSinSubasta();
    public Obra findById(Integer pkCod_Producto);
    public void create (Obra obra);
    public void update (Obra obra);
    public void delete (Obra obra);
}
