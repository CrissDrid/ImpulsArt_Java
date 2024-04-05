package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Subasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository

public interface SubastaRepository extends JpaRepository<Subasta, Long> {

    List<Subasta> findByEstadoSubastaContainingIgnoreCase(String estadoSubasta);

}
