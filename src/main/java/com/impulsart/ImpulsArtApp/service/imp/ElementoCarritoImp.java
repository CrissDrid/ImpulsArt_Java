package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.*;
import com.impulsart.ImpulsArtApp.repositories.CategoriaRepository;
import com.impulsart.ImpulsArtApp.repositories.ElementoCarritoRepository;
import com.impulsart.ImpulsArtApp.service.ElementoCarritoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementoCarritoImp implements ElementoCarritoService {
    @Autowired
    private ElementoCarritoRepository elementoCarritoRepository;

    @Override
    public ElementoCarrito findByCarritoAndObra(Carrito carrito, Obra obra) {
        if (carrito == null || obra == null) {
            throw new IllegalArgumentException("Carrito u Obra no pueden ser nulos");
        }

        return elementoCarritoRepository.findByCarritoAndObra(carrito, obra);
    }

    @Override
    public List<ElementoCarrito> findAll() throws Exception {
        return this.elementoCarritoRepository.findAll();
    }

    @Override
    public ElementoCarrito findById(Long pkCod_Categoria) {
        return this.elementoCarritoRepository.findById(pkCod_Categoria).orElse(null);
    }

    @Override
    public void create(ElementoCarrito elementoCarrito) {
        this.elementoCarritoRepository.save(elementoCarrito);
    }

    @Override
    public void update(ElementoCarrito elementoCarrito) {
        this.elementoCarritoRepository.save(elementoCarrito);
    }

    @Override
    public void delete(ElementoCarrito elementoCarrito) {
        this.elementoCarritoRepository.delete(elementoCarrito);
    }

    @Override
    public List<ElementoCarrito> findByCarrito(Carrito carrito) {
        List<ElementoCarrito> elementoCarritos = this.elementoCarritoRepository.findByCarrito(carrito);
        if (elementoCarritos.isEmpty()) {
            throw new EntityNotFoundException("Obras no encontradas");
        }
        return elementoCarritos;
    }
}
