package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    List<Usuario> findByEmail(@Param("email") String email);

    // Verificar si es asesor
    boolean existsByEmpleadosAsesorIsNotNullAndIdentificacion(int identificacion);

    // Verificar si es domiciliario
    boolean existsByEmpleadosDomiciliarioIsNotNullAndIdentificacion(int identificacion);
}
