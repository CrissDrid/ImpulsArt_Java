package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Despacho;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.repositories.DespachoRepository;
import com.impulsart.ImpulsArtApp.service.DespachoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespachoImp implements DespachoService {

    @Autowired
    private UsuarioImp usuarioImp;
    @Autowired
    private DespachoRepository despachoRepository;

    @Override
    public List<Despacho> findAll() throws Exception {
        return this.despachoRepository.findAll();
    }

    @Override
    public Usuario asignarDespachoAUsuario(Long pkCod_Despacho, int identificacion) {
        // Buscar el despacho por ID
        Despacho despacho = despachoRepository.findById(pkCod_Despacho)
                .orElseThrow(() -> new RuntimeException("Despacho no encontrado"));

        // Buscar el usuario por identificación
        Usuario usuario = usuarioImp.findById(identificacion);

        // Asignar el despacho al usuario
        usuario.getDespacho().add(despacho);

        // Guardar la asignación en la base de datos
        return usuarioImp.create(usuario);
    }

    @Override
    public List<Despacho> findDespachos() {
        List<Despacho> despachos = despachoRepository.findDespachos();

        // Verificar si no hay despachos asignados
        if (despachos == null || despachos.isEmpty()) {
            throw new RuntimeException("No hay despachos");
        }

        return despachos;
    }

    @Override
    public List<Despacho> findDespachosAsignados(int identificacion) {
        List<Despacho> despachos = despachoRepository.findDespachosAsignados(identificacion);

        // Verificar si no hay despachos asignados
        if (despachos == null || despachos.isEmpty()) {
            throw new RuntimeException("No hay despachos asignados para este usuario");
        }

        return despachos;
    }

    @Override
    public Despacho findById(Long pkCod_Despacho) {
        return this.despachoRepository.findById(pkCod_Despacho).orElse(null);
    }

    @Override
    public void create(Despacho despacho) {
        this.despachoRepository.save(despacho);
    }

    @Override
    public void update(Despacho despacho) {
        this.despachoRepository.save(despacho);
    }

    @Override
    public void delete(Despacho despacho) {
        this.despachoRepository.delete(despacho);
    }

}
