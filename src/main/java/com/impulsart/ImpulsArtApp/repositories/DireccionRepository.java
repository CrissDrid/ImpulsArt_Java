package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Direccion;
import com.impulsart.ImpulsArtApp.entities.Subasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {

    @Query("SELECT d FROM Direccion d WHERE d.usuario.identificacion = :identificacion")
    List<Direccion> findHistorialDireccion(@Param("identificacion") Integer identificacion);

}
