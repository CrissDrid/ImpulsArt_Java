package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Despacho;
import com.impulsart.ImpulsArtApp.entities.Domiciliario;
import com.impulsart.ImpulsArtApp.repositories.DespachoRepository;
import com.impulsart.ImpulsArtApp.repositories.DomiciliarioRepository;
import com.impulsart.ImpulsArtApp.service.DomiciliarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomiciliarioImp implements DomiciliarioService {

    @Autowired
    private DomiciliarioRepository domiciliarioRepository;

    @Override
    public List<Domiciliario> findAll() throws Exception {
        return this.domiciliarioRepository.findAll();
    }

    @Override
    public Domiciliario findById(Long pkCod_Domiciliario) {
        return this.domiciliarioRepository.findById(pkCod_Domiciliario).orElse(null);
    }

    @Override
    public void create(Domiciliario domiciliario) {
        this.domiciliarioRepository.save(domiciliario);
    }

    @Override
    public void update(Domiciliario domiciliario) {
        this.domiciliarioRepository.save(domiciliario);
    }

    @Override
    public void delete(Domiciliario domiciliario) {
        this.domiciliarioRepository.delete(domiciliario);
    }

}
