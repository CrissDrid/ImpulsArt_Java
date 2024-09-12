package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.ReporteObra;
import com.impulsart.ImpulsArtApp.entities.Subasta;
import com.impulsart.ImpulsArtApp.repositories.ReporteObraRepository;
import com.impulsart.ImpulsArtApp.service.ReporteObraService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteObraImp implements ReporteObraService {
    @Autowired
    private ReporteObraRepository reporteObraRepository;

    @Override
    public List<ReporteObra> findAll() throws Exception {
        return this.reporteObraRepository.findAll();
    }

    @Override
    public ReporteObra findById(Long pkCod_Reporte) {
        return this.reporteObraRepository.findById(pkCod_Reporte).orElse(null);
    }

    @Override
    public List<ReporteObra> findReportesObrasPorRevisar() {
        return this.reporteObraRepository.findAll();
    }

    @Override
    public void create(ReporteObra reporteObra) {
        this.reporteObraRepository.save(reporteObra);
    }

    @Override
    public void update(ReporteObra reporteObra) {
        this.reporteObraRepository.save(reporteObra);
    }

    @Override
    public void delete(ReporteObra reporteObra) {
        this.reporteObraRepository.delete(reporteObra);
    }
}
