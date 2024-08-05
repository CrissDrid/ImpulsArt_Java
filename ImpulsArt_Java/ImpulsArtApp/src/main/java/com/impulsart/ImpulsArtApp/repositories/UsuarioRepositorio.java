package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String email);

    // Verificar si es asesor
    boolean existsByEmpleadosAsesorIsNotNullAndIdentificacion(int identificacion);

    // Verificar si es domiciliario
    boolean existsByEmpleadosDomiciliarioIsNotNullAndIdentificacion(int identificacion);

}
