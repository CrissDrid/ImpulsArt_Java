package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Rol;
import com.impulsart.ImpulsArtApp.repositories.RolRepository;
import com.impulsart.ImpulsArtApp.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolImp implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> findAll() throws Exception {
        return this.rolRepository.findAll();
    }

    @Override
    public Rol findById(Long pkCod_Rol) {
        return this.rolRepository.findById(pkCod_Rol).orElse(null);
    }

    @Override
    public List<Rol> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }

    @Override
    public void create(Rol rol) {
        this.rolRepository.save(rol);
    }

    @Override
    public void update(Rol rol) {
        this.rolRepository.save(rol);
    }

    @Override
    public void delete(Rol rol) {
        this.rolRepository.delete(rol);
    }

}
