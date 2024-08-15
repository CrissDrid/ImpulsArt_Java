package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.ReporteObra;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReporteObraService {
    public List<ReporteObra> findAll() throws Exception;
    List<ReporteObra> findObrasConReportesPorUsuario(@Param("identificacion") Integer identificacion);
    public ReporteObra findById(Long pkCod_Reporte);
    public void create (ReporteObra reporteObra);
    public void update (ReporteObra reporteObra);
    public void delete (ReporteObra reporteObra);
}
