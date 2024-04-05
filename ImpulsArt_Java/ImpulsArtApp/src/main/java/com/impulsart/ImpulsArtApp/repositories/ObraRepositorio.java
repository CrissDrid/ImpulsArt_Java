package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObraRepositorio extends JpaRepository <Obra ,Integer> {

    List<Obra> findByCategoriaContainingIgnoreCase(String categoria);

    List<Obra> findByNombreProductoContainingIgnoreCase(String nombreProducto);

    List<Obra> findByNombreProductoContainingIgnoreCaseAndCategoriaContainingIgnoreCase(String nombreProducto, String categoria);

}
