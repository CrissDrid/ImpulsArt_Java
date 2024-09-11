package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    // @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    // List<Usuario> findByEmail(@Param("email") String email);

    //Metodo para buscar un usuario aleatoriamente con el rol ASESOR
    @Query(value = "SELECT u FROM Usuario u JOIN u.rol r WHERE r.nombre = 'ASESOR' ORDER BY RAND() LIMIT 1")
    Usuario findRandomAsesor();
    //Metodo para buscar un usuario aleatoriamente con el rol ASESOR

    //Metodo para buscar un usuario aleatoriamente con el rol DOMICILIARIO
    @Query(value = "SELECT u FROM Usuario u JOIN u.rol r WHERE r.nombre = 'DOMICILIARIO' ORDER BY RAND() LIMIT 1")
    Usuario findRandomDomiciliario();
    //Metodo para buscar un usuario aleatoriamente con el rol DOMICILIARIO


    //Metodo para buscar un usuario mediante su nombre
    Usuario findByEmail(String email);

    //Verificar si existe el usuario mediante el email
    Boolean existsByEmail(String email);

    List<Usuario> findByRolNombre(String nombreRol);

}
