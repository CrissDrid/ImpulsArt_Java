package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Carrito;
import com.impulsart.ImpulsArtApp.entities.Direccion;
import com.impulsart.ImpulsArtApp.entities.ElementoCarrito;
import com.impulsart.ImpulsArtApp.entities.Obra;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ElementoCarritoService {

    public ElementoCarrito findByCarritoAndObra(Carrito carrito, Obra obra);
    public List<ElementoCarrito> findAll() throws Exception;
    public ElementoCarrito findById(Long PkCod_Elemento);
    public void create(ElementoCarrito elementoCarrito);
    public void update(ElementoCarrito elementoCarrito);
    public void delete(ElementoCarrito elementoCarrito);
    public List<ElementoCarrito> findByCarrito(Carrito carrito);

}
