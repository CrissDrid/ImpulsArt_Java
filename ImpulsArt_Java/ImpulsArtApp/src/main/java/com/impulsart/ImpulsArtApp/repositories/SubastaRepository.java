package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Subasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository

public interface SubastaRepository extends JpaRepository<Subasta, Long> {

    @Query("SELECT s FROM Subasta s INNER JOIN s.obras o WHERE s.pkCodSubasta = :pkCodSubasta")
    List<Subasta> findSubastaByIdWithObras(@Param("pkCodSubasta") Long pkCodSubasta);
    @Query("SELECT s FROM Subasta s INNER JOIN s.obras o")
    List<Subasta>findSubastaAndObras();
    List<Subasta> findByEstadoSubastaContainingIgnoreCase(String estadoSubasta);

}
