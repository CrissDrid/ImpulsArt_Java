package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.PedidoPersonalizado;

import java.util.List;

public interface PedidoPersonalizadoService {
    public List<PedidoPersonalizado> findAll() throws Exception;
    public PedidoPersonalizado findById(Integer pk_CodPedido);
    public void create (PedidoPersonalizado pedidoPersonalizado);
    public void update(PedidoPersonalizado pedidoPersonalizado);
    public void delete(PedidoPersonalizado pedidoPersonalizado);
}
