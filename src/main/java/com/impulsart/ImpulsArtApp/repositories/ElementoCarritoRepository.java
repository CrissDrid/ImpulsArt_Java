package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Carrito;
import com.impulsart.ImpulsArtApp.entities.ElementoCarrito;
import com.impulsart.ImpulsArtApp.entities.Obra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElementoCarritoRepository extends JpaRepository<ElementoCarrito, Long> {

    ElementoCarrito findByCarritoAndObra(Carrito carrito, Obra obra);

    List<ElementoCarrito> findByCarrito(Carrito carrito);

}
