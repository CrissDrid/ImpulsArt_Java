package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Carrito;
import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.repositories.CarritoRepository;
import com.impulsart.ImpulsArtApp.repositories.ObraRepositorio;
import com.impulsart.ImpulsArtApp.service.CarritoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoImp implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ObraRepositorio obraRepositorio;

    @Override
    public Carrito findByUsuarioId(Integer identificacion) {
        return this.carritoRepository.findByUsuarioId(identificacion);
    }

    @Override
    public List<Carrito> findAll() throws Exception {
        return carritoRepository.findAll();
    }

    @Override
    public Carrito findById(Long PkCod_Carrito) throws Exception {
        return carritoRepository.findById(PkCod_Carrito)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));
    }

    @Override
    public void addObraToCarrito(Long carritoId, Integer obraId) throws Exception {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));

        Obra obra = obraRepositorio.findById(obraId)
                .orElseThrow(() -> new EntityNotFoundException("Obra no encontrada"));

        carrito.getObra().add(obra);
        carritoRepository.save(carrito);
    }

    @Override
    public void create(Carrito carrito) throws Exception {
        carritoRepository.save(carrito);
    }

    @Override
    public void update(Carrito carrito) throws Exception {
        carritoRepository.save(carrito);
    }

    @Override
    public void delete(Carrito carrito) throws Exception {
        carritoRepository.delete(carrito);
    }

    @Override
    public void removeObraFromCarrito(Long carritoId, Integer obraId) throws Exception {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));

        Obra obra = obraRepositorio.findById(obraId)
                .orElseThrow(() -> new EntityNotFoundException("Obra no encontrada"));

        if (carrito.getObra().remove(obra)) {
            carritoRepository.save(carrito);
        } else {
            throw new Exception("La obra no estaba en el carrito");
        }
    }
}


