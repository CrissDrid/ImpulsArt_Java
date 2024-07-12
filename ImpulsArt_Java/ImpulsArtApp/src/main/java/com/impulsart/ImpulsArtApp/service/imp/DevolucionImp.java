package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Reembolso;
import com.impulsart.ImpulsArtApp.repositories.DevolucionRepository;
import com.impulsart.ImpulsArtApp.service.DevolucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevolucionImp implements DevolucionService {


    @Autowired
    private DevolucionRepository devolucionRepository;

    @Override
    public List<Reembolso> findAll() throws Exception {
        return this.devolucionRepository.findAll();
    }

    @Override
    public Reembolso findById(Long pk_CodDevolucion) {
        return this.devolucionRepository.findById(pk_CodDevolucion).orElse(null);
    }

    @Override
    public void create(Reembolso reembolso) {
        this.devolucionRepository.save(reembolso);
    }

    @Override
    public void update(Reembolso reembolso) {
        this.devolucionRepository.save(reembolso);
    }

    @Override
    public void delete(Reembolso reembolso) {
        this.devolucionRepository.delete(reembolso);
    }
}
