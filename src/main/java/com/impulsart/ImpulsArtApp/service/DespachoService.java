package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Despacho;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DespachoService {

    public List<Despacho> findAll() throws Exception;
    Usuario asignarDespachoAUsuario(Long pkCod_Despacho, int identificacion);
    List<Despacho> findDespachos();
    List<Despacho> findDespachosAsignados(@Param("identificacion") int identificacion);
    List<Despacho> findDespachosEntregados(@Param("identificacion") int identificacion);
    public Despacho findById(Long pkCod_Despacho);
    public void create (Despacho despacho);
    public void update (Despacho despacho);
    public void delete (Despacho despacho);

}
