package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Respuesta;
import com.impulsart.ImpulsArtApp.repositories.RespuestaRepository;
import com.impulsart.ImpulsArtApp.service.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespuestaImp implements RespuestaService {
    @Autowired
    private RespuestaRepository respuestaRepository;

    @Override
    public List<Respuesta> findAll() throws Exception {
        return this.respuestaRepository.findAll();
    }

    @Override
    public Respuesta findById(Long pkCod_Respuesta) {
        return this.respuestaRepository.findById(pkCod_Respuesta).orElse(null);
    }

    @Override
    public void create(Respuesta respuesta) {
        this.respuestaRepository.save(respuesta);
    }

    @Override
    public void update(Respuesta respuesta) {
        this.respuestaRepository.save(respuesta);
    }

    @Override
    public void delete(Respuesta respuesta) {
        this.respuestaRepository.delete(respuesta);
    }
}
