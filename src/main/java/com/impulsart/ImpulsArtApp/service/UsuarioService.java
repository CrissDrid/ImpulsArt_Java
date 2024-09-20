package com.impulsart.ImpulsArtApp.service;

import com.impulsart.ImpulsArtApp.entities.Usuario;

import java.util.List;

public interface UsuarioService {

    public Usuario findByVerificationToken(String token);
    public List<Usuario> findAll() throws Exception;
    public Usuario findById(Long identificacion);
    public Usuario findRandomAsesor();
    List<Usuario> findByRolNombre(String nombreRol);

    //public List<Usuario> findByEmail(String email);
    public Usuario findByEmail(String email);
    Boolean existsByEmail(String email);
    public Usuario create(Usuario usuario);
    public void update(Usuario usuario);
    public void delete(Usuario usuario);
}
