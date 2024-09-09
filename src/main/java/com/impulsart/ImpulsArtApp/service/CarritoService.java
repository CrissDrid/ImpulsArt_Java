package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Carrito;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarritoService {

    Carrito findByUsuarioId(@Param("identificacion") Integer identificacion);

    List<Carrito> findAll() throws Exception;

    Carrito findById(Long PkCod_Carrito) throws Exception;

    void create(Carrito carrito) throws Exception;

    void update(Carrito carrito) throws Exception;

    void delete(Carrito carrito) throws Exception;

    void addObraToCarrito(Long carritoId, Integer obraId, Integer cantidad) throws Exception;

    void removeObraFromCarrito(Long carritoId, Integer obraId);

    void updateCantidadCarrito(Long carritoId, Long elementoId, Integer nuevaCantidad);
}

