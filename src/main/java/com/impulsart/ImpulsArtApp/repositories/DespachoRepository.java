package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Despacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespachoRepository extends JpaRepository<Despacho, Long> {

    @Query("SELECT d FROM Despacho d WHERE d.usuario IS EMPTY")
    List<Despacho> findDespachos();

    @Query("SELECT d FROM Despacho d JOIN d.usuario u WHERE u.identificacion = :identificacion")
    List<Despacho> findDespachosAsignados(@Param("identificacion") int identificacion);

    @Query("SELECT COUNT(d) FROM Despacho d")
    int contarDespachos();

}
