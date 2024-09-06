package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
