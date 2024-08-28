package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.repositories.UsuarioRepositorio;
import com.impulsart.ImpulsArtApp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioImp implements UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public List<Usuario> findAll() throws Exception {
        return this.usuarioRepositorio.findAll();
    }

    @Override
    public Usuario findById(Integer identificacion) {
        return this.usuarioRepositorio.findById(identificacion).orElse(null);
    }

    @Override
    public List<Usuario> findByEmail(String email) {
        return usuarioRepositorio.findByEmail(email);
    }

    @Override
    public void create(Usuario usuario) {
        this.usuarioRepositorio.save(usuario);
    }

    @Override
    public void update(Usuario usuario) {
        this.usuarioRepositorio.save(usuario);
    }

    @Override
    public void delete(Usuario usuario) {
        this.usuarioRepositorio.delete(usuario);
    }
}
