package com.impulsart.ImpulsArtApp.service.imp;

import com.impulsart.ImpulsArtApp.dto.EstadisticasDTO;
import com.impulsart.ImpulsArtApp.repositories.*;
import com.impulsart.ImpulsArtApp.service.EstadisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadisticasServiceImp implements EstadisticaService {

    @Autowired
    private VentaRepositorio ventaRepository;

    @Autowired
    private SubastaRepository subastaRepository;

    @Autowired
    private DespachoRepository despachoRepository;

    @Autowired
    private PqrsRepository pqrsRepository;

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    @Autowired
    private ReporteObraRepository reporteObraRepository;

    @Override
    public EstadisticasDTO obtenerEstadisticas() {
        // Obtener los conteos desde la base de datos usando los repositorios
        int ventas = ventaRepository.contarVentas();
        int subastas = subastaRepository.contarSubastas();
        int despachos = despachoRepository.contarDespachos();
        int pqrs = pqrsRepository.contarPqrs();
        int usuarios = usuarioRepository.contarUsuarios();
        int reportes = reporteObraRepository.contarReporte();

        // Crear el DTO con los datos obtenidos
        return new EstadisticasDTO(ventas, subastas, despachos, pqrs, usuarios,reportes);
    }

}
