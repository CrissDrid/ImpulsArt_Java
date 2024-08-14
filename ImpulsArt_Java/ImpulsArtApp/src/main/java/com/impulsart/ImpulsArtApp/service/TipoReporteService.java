package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Respuesta;
import com.impulsart.ImpulsArtApp.entities.TipoReporte;

import java.util.List;

public interface TipoReporteService {
    public List<TipoReporte> findAll() throws Exception;
    public TipoReporte findById(Long pkCod_TipoReporte);
    public void create (TipoReporte tipoReporte);
    public void update (TipoReporte tipoReporte);
    public void delete (TipoReporte tipoReporte);

}
