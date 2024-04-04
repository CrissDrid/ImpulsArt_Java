package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Despacho;
import com.impulsart.ImpulsArtApp.repositories.DespachoRepository;
import com.impulsart.ImpulsArtApp.service.DespachoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespachoImp implements DespachoService {

    @Autowired
    private DespachoRepository despachoRepository;

    @Override
    public List<Despacho> findAll() throws Exception {
        return this.despachoRepository.findAll();
    }

    @Override
    public Despacho findById(Long pkCod_Despacho) {
        return this.despachoRepository.findById(pkCod_Despacho).orElse(null);
    }

    @Override
    public void create(Despacho despacho) {
        this.despachoRepository.save(despacho);
    }

    @Override
    public void update(Despacho despacho) {
        this.despachoRepository.save(despacho);
    }

    @Override
    public void delete(Despacho despacho) {
        this.despachoRepository.delete(despacho);
    }

}
