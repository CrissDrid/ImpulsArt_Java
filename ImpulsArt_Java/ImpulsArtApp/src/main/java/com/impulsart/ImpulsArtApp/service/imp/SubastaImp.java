package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Subasta;
import com.impulsart.ImpulsArtApp.repositories.SubastaRepository;
import com.impulsart.ImpulsArtApp.service.SubastaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service

public class SubastaImp implements SubastaService {

    @Autowired
    private SubastaRepository subastaRepository;

    @Override
    public List<Subasta> findSubastaByIdWithObras(Long pkCodSubasta) {
        List<Subasta> subastas = this.subastaRepository.findSubastaByIdWithObras(pkCodSubasta);
        if (subastas.isEmpty()) {
            throw new EntityNotFoundException("Subasta no encontrada");
        }
        return subastas;
    }

    @Override
    public List<Subasta> findSubastaAndObras() {
        return this.subastaRepository.findSubastaAndObras();
    }

    @Override
    public List<Subasta> findAll() throws Exception {
        return this.subastaRepository.findAll();
    }

    @Override
    public Subasta findById(Long pkCodSubasta) {
        return this.subastaRepository.findById(pkCodSubasta).orElse(null);
    }

    @Override
    public void create(Subasta subasta) {
        this.subastaRepository.save(subasta);
    }

    @Override
    public void delete(Subasta subasta) {
        this.subastaRepository.delete(subasta);
    }

    @Override
    public void update(Subasta subasta) {
        this.subastaRepository.save(subasta);
    }

    @Override
    public List<Subasta> findByEstadoSubastaContainingIgnoreCase(String estadoSubasta) {
        return subastaRepository.findByEstadoSubastaContainingIgnoreCase(estadoSubasta);
    }

}
