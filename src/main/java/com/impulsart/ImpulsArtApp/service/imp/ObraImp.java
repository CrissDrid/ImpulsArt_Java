package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.repositories.ObraRepositorio;
import com.impulsart.ImpulsArtApp.service.ObraService;
import jakarta.persistence.EntityNotFoundException;
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
    public List<Obra> findHistorialObras(Integer identificacion) {
        List<Obra> obras = this.obraRepositorio.findHistorialObras(identificacion);
        if (obras.isEmpty()) {
            throw new EntityNotFoundException("Obras no encontradas");
        }
        return obras;
    }

    public List<Obra> findByNombreProductoContainingIgnoreCase(String nombreProducto) {
        return obraRepositorio.findByNombreProductoContainingIgnoreCase(nombreProducto);
    }

    @Override
    public List<Obra> findObrasSinSubasta() {
        return obraRepositorio.findObrasSinSubasta();
    }

    @Override
    public Obra findById(Integer pkCod_Producto) {
        return this.obraRepositorio.findById(pkCod_Producto).orElse(null);
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
