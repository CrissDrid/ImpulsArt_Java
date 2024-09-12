package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.ReporteObra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReporteObraRepository extends JpaRepository<ReporteObra, Long> {

    // Buscar reportes de obras con estado "Por revisar"
    @Query("SELECT r FROM ReporteObra r WHERE r.estado = 'Por resolver'")
    List<ReporteObra> findReportesObrasPorRevisar();


}
