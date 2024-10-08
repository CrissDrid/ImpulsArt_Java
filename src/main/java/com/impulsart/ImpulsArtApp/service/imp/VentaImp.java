package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Venta;
import com.impulsart.ImpulsArtApp.repositories.VentaRepositorio;
import com.impulsart.ImpulsArtApp.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaImp implements VentaService {

    @Autowired
    private VentaRepositorio ventaRepositorio;
    @Override
    public List<Venta> findAll() throws Exception {
        return this.ventaRepositorio.findAll();
    }

    @Override
    public Venta findById(Integer PkCod_Venta) {
        return this.ventaRepositorio.findById(PkCod_Venta).orElse(null);
    }

    @Override
    public void create(Venta venta) {
        this.ventaRepositorio.save(venta);
    }

    @Override
    public void update(Venta venta) {
        this.ventaRepositorio.save(venta);
    }

    @Override
    public void delete(Venta venta) {
        this.ventaRepositorio.delete(venta);
    }
}
