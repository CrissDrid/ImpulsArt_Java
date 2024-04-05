package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.repositories.ObraRepositorio;
import com.impulsart.ImpulsArtApp.service.ObraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObraImp implements ObraService {

    @Autowired
    private ObraRepositorio obraRepositorio;

    @Override
    public List<Obra> findAll() throws Exception {
        return this.obraRepositorio.findAll();
    }

    @Override
    public List<Obra> findByCategoriaContainingIgnoreCase(String categoria) {
        return obraRepositorio.findByCategoriaContainingIgnoreCase(categoria);
    }

    public List<Obra> findByNombreProductoContainingIgnoreCase(String nombreProducto) {
        return obraRepositorio.findByNombreProductoContainingIgnoreCase(nombreProducto);
    }

    @Override
    public List<Obra> findByNombreProductoContainingIgnoreCaseAndCategoriaContainingIgnoreCase(String nombreProducto, String categoria) {
        return obraRepositorio.findByNombreProductoContainingIgnoreCaseAndCategoriaContainingIgnoreCase(nombreProducto, categoria);
    }

    @Override
    public Obra findById(Integer PkCod_Producto) {
        return this.obraRepositorio.findById(PkCod_Producto).orElse(null);
    }

    @Override
    public void create(Obra obra) {
        this.obraRepositorio.save(obra);
    }

    @Override
    public void update(Obra obra) {
        this.obraRepositorio.save(obra);
    }

    @Override
    public void delete(Obra obra) {
        this.obraRepositorio.delete(obra);
    }
}
