package com.impulsart.ImpulsArtApp.repository;

import com.impulsart.ImpulsArtApp.entities.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OfertaRepository extends JpaRepository<Oferta, Long> {
}
