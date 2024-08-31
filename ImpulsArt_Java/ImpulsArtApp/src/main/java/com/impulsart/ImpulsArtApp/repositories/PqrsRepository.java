package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Pqrs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PqrsRepository extends JpaRepository<Pqrs, Long> {

    @Query("SELECT r FROM Pqrs r JOIN r.usuarios u WHERE u.identificacion = :identificacion")
    List<Pqrs> findHistorialPqrs(@Param("identificacion") Integer identificacion);

}
