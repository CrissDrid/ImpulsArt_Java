package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Carrito;
import com.impulsart.ImpulsArtApp.repositories.CarritoRepository;
import com.impulsart.ImpulsArtApp.service.CarritoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoImp implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

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
}


