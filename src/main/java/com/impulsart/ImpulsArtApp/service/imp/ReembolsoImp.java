package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Reembolso;
import com.impulsart.ImpulsArtApp.repositories.ReembolsoRepository;
import com.impulsart.ImpulsArtApp.service.ReembolsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReembolsoImp implements ReembolsoService {


    @Autowired
    private ReembolsoRepository reembolsoRepository;

    @Override
    public List<Reembolso> findAll() throws Exception {
        return this.reembolsoRepository.findAll();
    }

    @Override
    public Reembolso findById(Long pk_CodReembolso) {
        return this.reembolsoRepository.findById(pk_CodReembolso).orElse(null);
    }

    @Override
    public void create(Reembolso reembolso) {
        this.reembolsoRepository.save(reembolso);
    }

    @Override
    public void update(Reembolso reembolso) {
        this.reembolsoRepository.save(reembolso);
    }

    @Override
    public void delete(Reembolso reembolso) {
        this.reembolsoRepository.delete(reembolso);
    }
}
