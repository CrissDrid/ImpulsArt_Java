package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Usuario;

import java.util.List;

public interface UsuarioService {
    public List<Usuario> findAll() throws Exception;
    public Usuario findById(Integer identificacion);

    //public List<Usuario> findByEmail(String email);
    public Usuario findByEmail(String email);
    Boolean existsByEmail(String email);
    public void create(Usuario usuario);
    public void update(Usuario usuario);
    public void delete(Usuario usuario);
}
