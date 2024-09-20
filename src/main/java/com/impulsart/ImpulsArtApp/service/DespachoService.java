package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Despacho;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DespachoService {

    public List<Despacho> findAll() throws Exception;
    Usuario asignarDespachoAUsuario(Long pkCod_Despacho, Long identificacion);
    List<Despacho> findDespachos();
    List<Despacho> findDespachosAsignados(@Param("identificacion") Long identificacion);
    List<Despacho> findDespachosEntregados(@Param("identificacion") Long identificacion);
    public Despacho findById(Long pkCod_Despacho);
    public void create (Despacho despacho);
    public void update (Despacho despacho);
    public void delete (Despacho despacho);

}
