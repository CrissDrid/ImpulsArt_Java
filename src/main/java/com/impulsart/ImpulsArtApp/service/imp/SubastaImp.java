package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Oferta;
import com.impulsart.ImpulsArtApp.entities.Subasta;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import com.impulsart.ImpulsArtApp.repositories.SubastaRepository;
import com.impulsart.ImpulsArtApp.service.SubastaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@EnableScheduling
@Service
public class SubastaImp implements SubastaService {

    @Autowired
    private SubastaRepository subastaRepository;

    @Autowired
    private OfertaImp ofertaImp;

    @Autowired
    private UsuarioImp usuarioImp;

    @Autowired
    private ObraImp obraImp;

    @Autowired
    private EmailImp emailImp;

    @Transactional
    @Scheduled(fixedRate = 3600000) // Cada hora
    @Override
    public String finalizarSubastas() {
        LocalDateTime ahora = LocalDateTime.now();
        List<Subasta> subastas = subastaRepository.findSubastasActivasParaFinalizar(ahora);

        for (Subasta subasta : subastas) {
            if (!"Finalizado".equals(subasta.getEstadoSubasta())) {
                subasta.setEstadoSubasta("Finalizado");
                subastaRepository.save(subasta);

                List<Oferta> ofertas = ofertaImp.findOfertasBySubasta(subasta.getPkCodSubasta());
                Usuario creador = subastaRepository.findUsuarioBySubastaId(subasta.getPkCodSubasta());
                String obraNombre = subasta.getObras().getNombreProducto();

                if (ofertas.isEmpty()) {
                    // Caso 1: No hay ofertas
                    emailImp.enviarCorreoSubasta(creador.getEmail(), "Finalización de Subasta", creador.getNombre(),
                            "La subasta para la obra '" + obraNombre + "' ha finalizado sin ofertas.");
                    System.out.println("Correo de no ofertas enviado a: " + creador.getEmail());
                } else {
                    // Caso 2: Hay ofertas
                    Oferta mejorOferta = ofertaImp.findOfertaMasAlta(subasta.getPkCodSubasta());
                    Usuario ganador = mejorOferta.getUsuarios();

                    // Informar al creador de la subasta
                    emailImp.enviarCorreoSubasta(creador.getEmail(), "Finalización de Subasta",
                            creador.getNombre(), "La subasta para la obra '" + obraNombre + "' ha finalizado. Ganador: " + ganador.getNombre() +
                                    ". Número de ofertas: " + ofertas.size() + ".");
                    System.out.println("Correo de finalización enviado al creador: " + creador.getEmail());

                    // Informar al ganador
                    emailImp.enviarCorreoSubasta(ganador.getEmail(), "Ganador de Subasta",
                            ganador.getNombre(), "Felicidades, has ganado la subasta para la obra '" + obraNombre + "' con una puja de: " + mejorOferta.getMonto() + ".");
                    System.out.println("Correo de ganador enviado a: " + ganador.getEmail());

                    // Informar a los demás participantes
                    for (Oferta oferta : ofertas) {
                        Usuario participante = oferta.getUsuarios();
                        if (!participante.equals(ganador)) {
                            emailImp.enviarCorreoSubasta(participante.getEmail(), "Resultado de Subasta",
                                    participante.getNombre(), "Lamentablemente, tu oferta para la obra '" + obraNombre + "' no fue la ganadora.");
                            System.out.println("Correo de no ganador enviado a: " + participante.getEmail());
                        }
                    }
                }
            }
        }

        return "Subastas finalizadas y correos enviados";
    }

    @Override
    public List<Obra> findHistorialObrasSubasta(Integer identificacion) {
        List<Obra> obras = this.subastaRepository.findHistorialObrasSubasta(identificacion);
        if (obras.isEmpty()) {
            throw new EntityNotFoundException("Subastas no encontradas");
        }
        return obras;
    }

    @Override
    public List<Obra> findSubastaByIdWithObras(Long pkCodSubasta) {
        List<Obra> obras = this.subastaRepository.findSubastaByIdWithObras(pkCodSubasta);
        if (obras.isEmpty()) {
            throw new EntityNotFoundException("Subasta no encontrada");
        }
        return obras;
    }

    @Override
    public Usuario findUsuarioBySubastaId(Long pkCodSubasta) {
        Usuario usuario = subastaRepository.findUsuarioBySubastaId(pkCodSubasta);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return usuario;
    }

    @Override
    public List<Subasta> findSubastasActivasParaFinalizar(LocalDateTime ahora) {
        List<Subasta> subastas = this.subastaRepository.findSubastasActivasParaFinalizar(ahora);
        return subastas;  // Devuelve la lista vacía en lugar de lanzar una excepción
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
