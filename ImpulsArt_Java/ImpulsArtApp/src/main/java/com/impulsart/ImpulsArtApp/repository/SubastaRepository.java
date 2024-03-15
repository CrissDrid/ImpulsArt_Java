package com.impulsart.ImpulsArtApp.repository;

import com.impulsart.ImpulsArtApp.entities.Subasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface SubastaRepository extends JpaRepository<Subasta, Long> {
}
