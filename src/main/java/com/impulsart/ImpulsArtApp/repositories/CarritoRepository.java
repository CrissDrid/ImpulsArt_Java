package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    @Query("SELECT c FROM Carrito c WHERE c.usuario.identificacion = :identificacion AND c.id NOT IN (SELECT v.carrito.id FROM Venta v)")
    Carrito findByUsuarioId(@Param("identificacion") Integer identificacion);


}
