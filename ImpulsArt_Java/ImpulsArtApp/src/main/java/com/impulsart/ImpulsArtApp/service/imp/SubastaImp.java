package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Subasta;
import com.impulsart.ImpulsArtApp.repository.OfertaRepository;
import com.impulsart.ImpulsArtApp.repository.SubastaRepository;
import com.impulsart.ImpulsArtApp.service.SubastaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SubastaImp implements SubastaService {

    @Autowired
    private SubastaRepository subastaRepository;

    @Override
    public List<Subasta> findAll() throws Exception {
        return this.subastaRepository.findAll();
    }

    @Override
    public void create(Subasta subasta) {
        this.subastaRepository.save(subasta);
    }

    @Override
    public void delete(Subasta subasta) {
        this.subastaRepository.save(subasta);
    }

    @Override
    public void update(Subasta subasta) {
        this.subastaRepository.save(subasta);
    }
}
