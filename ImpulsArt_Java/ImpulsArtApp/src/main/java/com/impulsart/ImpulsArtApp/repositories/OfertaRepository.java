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

    @Query(value = "SELECT o FROM Oferta o INNER JOIN o.Subastas s WHERE s.pkCodSubasta = :pkCodSubasta")
    List<Oferta> findOfertasBySubasta(@Param("pkCodSubasta") Long pkCodSubasta);

}
