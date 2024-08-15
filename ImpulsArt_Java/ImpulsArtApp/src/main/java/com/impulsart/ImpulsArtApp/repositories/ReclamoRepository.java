package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Reclamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReclamoRepository extends JpaRepository<Reclamo, Long> {

    @Query("SELECT r FROM Reclamo r JOIN r.usuarios u WHERE u.identificacion = :identificacion")
    List<Reclamo> findHistorialReclamos(@Param("identificacion") Integer identificacion);

}
