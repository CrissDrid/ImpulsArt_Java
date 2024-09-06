package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.TipoReporte;
import com.impulsart.ImpulsArtApp.repositories.TipoReporteRepository;
import com.impulsart.ImpulsArtApp.service.TipoReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoReporteImp implements TipoReporteService {
    @Autowired
    private TipoReporteRepository tipoReporteRepository;

    @Override
    public List<TipoReporte> findAll() throws Exception {
        return this.tipoReporteRepository.findAll();
    }

    @Override
    public TipoReporte findById(Long pkCod_TipoReporte) {
        return this.tipoReporteRepository.findById(pkCod_TipoReporte).orElse(null);
    }

    @Override
    public void create(TipoReporte tipoReporte) {
        this.tipoReporteRepository.save(tipoReporte);
    }

    @Override
    public void update(TipoReporte tipoReporte) {
        this.tipoReporteRepository.save(tipoReporte);
    }

    @Override
    public void delete(TipoReporte tipoReporte) {
        this.tipoReporteRepository.delete(tipoReporte);
    }
}
