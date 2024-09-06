package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.RegistroDespacho;
import com.impulsart.ImpulsArtApp.repositories.RegistroDespachoRepository;
import com.impulsart.ImpulsArtApp.service.RegistroDespachoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroDespachoImp implements RegistroDespachoService {

    @Autowired
    private RegistroDespachoRepository registroDespachoRepository;

    @Override
    public List<RegistroDespacho> findAll() throws Exception {
        return this.registroDespachoRepository.findAll();
    }

    @Override
    public RegistroDespacho findById(Long pkCod_registro) {
        return this.registroDespachoRepository.findById(pkCod_registro).orElse(null);
    }

    @Override
    public void create(RegistroDespacho registroDespacho) {
        this.registroDespachoRepository.save(registroDespacho);
    }

    @Override
    public void update(RegistroDespacho registroDespacho) {
        this.registroDespachoRepository.save(registroDespacho);
    }

    @Override
    public void delete(RegistroDespacho registroDespacho) {
        this.registroDespachoRepository.delete(registroDespacho);
    }

}
