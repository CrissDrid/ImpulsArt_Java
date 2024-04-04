package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.PedidoPersonalizado;
import com.impulsart.ImpulsArtApp.repositories.PedidoPersonalizadoRepositorio;
import com.impulsart.ImpulsArtApp.service.PedidoPersonalizadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoPersonalizadoImp implements PedidoPersonalizadoService {
    @Autowired
    private PedidoPersonalizadoRepositorio pedidoPersonalizadoRepositorio;

    @Override
    public List<PedidoPersonalizado> findAll() throws Exception {
        return this.pedidoPersonalizadoRepositorio.findAll();
    }

    @Override
    public PedidoPersonalizado findById(Integer pk_CodPedido) {
        return this.pedidoPersonalizadoRepositorio.findById(pk_CodPedido).orElse(null);
    }

    @Override
    public void create(PedidoPersonalizado pedidoPersonalizado) {
        this.pedidoPersonalizadoRepositorio.save(pedidoPersonalizado);
    }

    @Override
    public void update(PedidoPersonalizado pedidoPersonalizado) {
        this.pedidoPersonalizadoRepositorio.save(pedidoPersonalizado);
    }

    @Override
    public void delete(PedidoPersonalizado pedidoPersonalizado) {
        this.pedidoPersonalizadoRepositorio.delete(pedidoPersonalizado);
    }
}
