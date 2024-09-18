package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepositorio extends JpaRepository <Venta ,Integer> {

    @Query("SELECT COUNT(v) FROM Venta v")
    int contarVentas();


}
