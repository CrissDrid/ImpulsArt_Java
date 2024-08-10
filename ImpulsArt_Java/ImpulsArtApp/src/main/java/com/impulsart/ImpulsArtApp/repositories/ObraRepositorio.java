package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObraRepositorio extends JpaRepository <Obra ,Integer> {

    @Query("SELECT o FROM Obra o WHERE o.pkCod_Producto NOT IN (SELECT s.obras.pkCod_Producto FROM Subasta s)")
    List<Obra> findObrasSinSubasta();

    List<Obra> findByNombreProductoContainingIgnoreCase(String nombreProducto);

}
