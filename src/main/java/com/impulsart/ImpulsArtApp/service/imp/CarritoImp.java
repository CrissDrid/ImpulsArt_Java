package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Carrito;
import com.impulsart.ImpulsArtApp.entities.ElementoCarrito;
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
    private ObraImp obraImp;

    @Autowired
    private ElementoCarritoImp elementoCarritoImp;

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
    public void addObraToCarrito(Long carritoId, Integer obraId, Integer cantidad) throws Exception {
        // Buscar el carrito por ID
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        // Buscar la obra por ID
        Obra obra = obraImp.findById(obraId);

        // Verificar si la cantidad solicitada está disponible
        if (obra.getCantidad() < cantidad) {
            throw new Exception("No hay suficiente cantidad de la obra disponible");
        }

        // Buscar el elemento del carrito que corresponde a la obra
        ElementoCarrito elementoCarrito = elementoCarritoImp.findByCarritoAndObra(carrito, obra);

        if (elementoCarrito != null) {
            // Si el elemento ya existe, actualizar la cantidad
            int cantidadActualEnCarrito = elementoCarrito.getCantidad();
            int cantidadNueva = cantidadActualEnCarrito + cantidad;

            // Calcular la cantidad total deseada en inventario después de la actualización
            int cantidadTotalDeseada = cantidadNueva;

            // Verificar si hay suficiente stock disponible para la nueva cantidad
            if (obra.getCantidad() < cantidadTotalDeseada) {
                throw new Exception("No hay suficiente stock disponible después de la actualización");
            }

            // Establecer la nueva cantidad
            elementoCarrito.setCantidad(cantidadNueva);
        } else {
            // Si el elemento no existe, crear uno nuevo
            if (obra.getCantidad() < cantidad) {
                throw new Exception("No hay suficiente cantidad de la obra disponible");
            }

            elementoCarrito = new ElementoCarrito();
            elementoCarrito.setCarrito(carrito);
            elementoCarrito.setObra(obra);
            elementoCarrito.setCantidad(cantidad);
        }

        // Guardar el elemento actualizado o nuevo
        elementoCarritoImp.create(elementoCarrito);
    }

    @Override
    public void removeObraFromCarrito(Long carritoId, Integer obraId) {
        // Buscar el carrito por ID
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        // Buscar la obra por ID
        Obra obra = obraImp.findById(obraId);

        // Buscar el elemento del carrito que corresponde a la obra
        ElementoCarrito elementoCarrito = elementoCarritoImp.findByCarritoAndObra(carrito, obra);

        if (elementoCarrito != null) {
            // Si el elemento existe en el carrito, eliminarlo
            elementoCarritoImp.delete(elementoCarrito);
        } else {
            throw new RuntimeException("El elemento no se encuentra en el carrito");
        }
    }

}


