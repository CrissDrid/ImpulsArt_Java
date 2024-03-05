package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Subastas")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Subasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCodSubasta", nullable = false)
    private Long pkCodSubasta;

    @Column(name = "EstadoSubasta", nullable = false, length = 50)
    private String EstadoSubasta;

    @Column(name = "PrecioInicial", nullable = false, length = 255)
    private int PrecioInicial;

    @Column(name = "FechaInicio", nullable = false)
    private LocalDate FechaInicio;

    @Column(name = "FechaFinalizacion", nullable = false)
    private LocalDate FechaFinalizacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FkCod_Producto",nullable = false)
    private Obra obras;

    @OneToMany(mappedBy = "Subastas")
    private List<Oferta> ofertas;

}
