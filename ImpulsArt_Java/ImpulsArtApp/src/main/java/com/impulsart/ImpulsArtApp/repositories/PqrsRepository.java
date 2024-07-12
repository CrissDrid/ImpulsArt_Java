package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Reclamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PqrsRepository extends JpaRepository<Reclamo, Long> {
}
