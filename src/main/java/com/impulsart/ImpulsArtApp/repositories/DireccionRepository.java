package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Direccion;
import com.impulsart.ImpulsArtApp.entities.Subasta;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {

    @Query("SELECT d FROM Direccion d WHERE d.usuario.identificacion = :identificacion")
    List<Direccion> findHistorialDireccion(@Param("identificacion") Long identificacion);

    //Verificar si existe la direccion
    // Verifica si ya existe una dirección para un usuario en una ciudad específica
    boolean existsByCiudadAndDireccionAndUsuario(String ciudad, String direccion, Usuario usuario);

}
