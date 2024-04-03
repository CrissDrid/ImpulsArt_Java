package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Domiciliario;
import com.impulsart.ImpulsArtApp.entities.Vehiculo;
import com.impulsart.ImpulsArtApp.repositories.DomiciliarioRepository;
import com.impulsart.ImpulsArtApp.repositories.VehiculoRepository;
import com.impulsart.ImpulsArtApp.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoImp implements VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Override
    public List<Vehiculo> findAll() throws Exception {
        return this.vehiculoRepository.findAll();
    }

    @Override
    public Vehiculo findById(Long pk_placa) {
        return this.vehiculoRepository.findById(pk_placa).orElse(null);
    }

    @Override
    public void create(Vehiculo vehiculo) {
        this.vehiculoRepository.save(vehiculo);
    }

    @Override
    public void update(Vehiculo vehiculo) {
        this.vehiculoRepository.save(vehiculo);
    }

    @Override
    public void delete(Vehiculo vehiculo) {
        this.vehiculoRepository.delete(vehiculo);
    }

}
