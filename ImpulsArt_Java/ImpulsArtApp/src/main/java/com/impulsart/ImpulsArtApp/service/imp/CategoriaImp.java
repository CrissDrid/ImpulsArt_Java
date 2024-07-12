package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Categoria;
import com.impulsart.ImpulsArtApp.repositories.CategoriaRepository;
import com.impulsart.ImpulsArtApp.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoriaImp implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> findAll() throws Exception {
        return this.categoriaRepository.findAll();
    }

    @Override
    public Categoria findById(Long pkCod_Categoria) {
        return this.categoriaRepository.findById(pkCod_Categoria).orElse(null);
    }

    @Override
    public void create(Categoria categoria) {
         this.categoriaRepository.save(categoria);
    }

    @Override
    public void update(Categoria categoria) {
        this.categoriaRepository.save(categoria);
    }

    @Override
    public void delete(Categoria categoria) {
        this.categoriaRepository.delete(categoria);
    }
}
