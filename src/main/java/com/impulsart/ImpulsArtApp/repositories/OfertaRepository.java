package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface OfertaRepository extends JpaRepository<Oferta, Long> {

    @Query("SELECT o FROM Oferta o WHERE o.monto = (SELECT MAX(o2.monto) FROM Oferta o2 WHERE o2.Subastas.pkCodSubasta = :pkCodSubasta) AND o.Subastas.pkCodSubasta = :pkCodSubasta")
    Oferta findOfertaMasAlta(@Param("pkCodSubasta") Long pkCodSubasta);

    @Query("SELECT o FROM Oferta o WHERE o.Subastas.pkCodSubasta = :pkCodSubasta AND o.monto < (SELECT MAX(o2.monto) FROM Oferta o2 WHERE o2.Subastas.pkCodSubasta = :pkCodSubasta) ORDER BY o.monto DESC")
    List<Oferta> findOfertasBySubasta(@Param("pkCodSubasta") Long pkCodSubasta);

    // Nuevo método para encontrar oferta por subasta y usuario
    @Query("SELECT o FROM Oferta o WHERE o.Subastas.pkCodSubasta = :subastaId AND o.Usuarios.identificacion = :usuarioId")
    Oferta findBySubastaIdAndUsuarioId(@Param("subastaId") Long subastaId, @Param("usuarioId") int usuarioId);
    // Nuevo método para encontrar oferta por subasta y usuario

}
