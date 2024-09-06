package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.ReporteObra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ReporteObraRepository extends JpaRepository<ReporteObra, Long> {

}
