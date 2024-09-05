package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Pqrs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PqrsRepository extends JpaRepository<Pqrs, Long> {

    //Historial de pqrs
    @Query("SELECT r FROM Pqrs r JOIN r.usuarios u WHERE u.identificacion = :identificacion")
    List<Pqrs> findHistorialPqrs(@Param("identificacion") Integer identificacion);
    //Historial de pqrs


    //Buscar pqrs asignado a asesores
    @Query("SELECT p FROM Pqrs p JOIN p.usuarios u JOIN u.rol r WHERE r.nombre = 'ASESOR' AND u.identificacion = :identificacion")
    List<Pqrs> findPqrsAsignadoAsesores(@Param("identificacion") Integer identificacion);
    //Buscar pqrs asignado a asesores

}
