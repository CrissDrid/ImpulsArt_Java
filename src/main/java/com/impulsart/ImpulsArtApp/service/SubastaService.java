package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Oferta;
import com.impulsart.ImpulsArtApp.entities.Subasta;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SubastaService {

    List<Subasta> findHistorialObrasSubasta(@Param("identificacion") Integer identificacion);
    List<Subasta> findSubastaByIdWithObras(@Param("pkCodSubasta") Long pkCodSubasta);
    Usuario findUsuarioBySubastaId(@Param("pkCodSubasta") Long pkCodSubasta);
    List<Subasta> findSubastasActivasParaFinalizar(@Param("ahora") LocalDateTime ahora);
    String finalizarSubastas();
    public List<Subasta> findAll() throws Exception;
    Subasta findById(Long pkCodSubasta);
    public void create(Subasta subasta);
    public void delete(Subasta subasta);
    public void update(Subasta subasta);

    List<Subasta> findByEstadoSubastaContainingIgnoreCase(String estadoSubasta);

}
