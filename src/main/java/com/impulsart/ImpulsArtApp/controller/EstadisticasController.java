package com.impulsart.ImpulsArtApp.controller;

import com.impulsart.ImpulsArtApp.dto.EstadisticasDTO;
import com.impulsart.ImpulsArtApp.service.EstadisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/estadisticas", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.HEAD})
@CrossOrigin("*")
public class EstadisticasController {
    @Autowired
    private EstadisticaService estadisticasService;

    @GetMapping("/obtener")
    public EstadisticasDTO obtenerEstadisticas() {
        return estadisticasService.obtenerEstadisticas();
    }
}
