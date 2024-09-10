package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.repositories.UsuarioRepositorio;
import com.impulsart.ImpulsArtApp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public Usuario findRandomAsesor() {
        Usuario usuario = usuarioRepositorio.findRandomAsesor();

        // Verificar si no hay asesores disponibles
        if (usuario == null) {
            throw new RuntimeException("No hay asesores disponibles para asignar.");
        }

        return usuario;
    }

    @Override
    public List<Usuario> findByRolNombre(String nombreRol) {
        List<Usuario> usuarios = usuarioRepositorio.findByRolNombre(nombreRol);
        if (usuarios.isEmpty()) {
            throw new UsernameNotFoundException("No se encontraron usuarios con el rol: " + nombreRol);
        }
        return usuarios;
    }

    @Override
    public Usuario findByEmail(String email) {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con nombre: " + email);
        }
        return usuario;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return usuarioRepositorio.existsByEmail(email);
    }

    @Override
    public Usuario create(Usuario usuario) {
        this.usuarioRepositorio.save(usuario);
        return usuario;
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
