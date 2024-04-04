package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Empleado;
import com.impulsart.ImpulsArtApp.repositories.EmpleadoRepository;
import com.impulsart.ImpulsArtApp.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmpleadoImp implements EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Override
    public List<Empleado> findAll() throws Exception {
        return this.empleadoRepository.findAll();
    }

    @Override
    public Empleado findById(Long pkCod_Empleado) {
        return this.empleadoRepository.findById(pkCod_Empleado).orElse(null);
    }

    @Override
    public void create(Empleado empleado) {
        this.empleadoRepository.save(empleado);
    }

    @Override
    public void update(Empleado empleado) {
        this.empleadoRepository.save(empleado);
    }

    @Override
    public void delete(Empleado empleado) {
        this.empleadoRepository.delete(empleado);
    }
}
