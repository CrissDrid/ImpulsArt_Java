package com.impulsart.ImpulsArtApp.security;

import com.impulsart.ImpulsArtApp.entities.Rol;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.service.imp.UsuarioImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUsersDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioImp usuarioImp;

    //Metodo para traer una lista de autoridades por medio de la lista de roles
    public Collection<GrantedAuthority> mapToAuthorities(Rol rol){
        return List.of(new SimpleGrantedAuthority(rol.getNombre()));
    }

    //Metodo para obtener los datos del usuario por medio del UserName
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Buscar usuario por nombre de usuario
        Usuario usuario = usuarioImp.findByEmail(email);

        return new User(usuario.getEmail(), usuario.getContrasena(), mapToAuthorities(usuario.getRol()));

    }
}
