package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private String estadoSubasta;

    @Column(name = "PrecioInicial", nullable = false, length = 255)
    private String precioInicial;

    @Column(name = "FechaInicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "FechaFinalizacion", nullable = false)
    private LocalDateTime fechaFinalizacion;

    //FOREING KEYS
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FkCod_Producto", nullable = false)
    private Obra obras;

    @OneToMany(mappedBy = "Subastas", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Oferta> ofertas;

}
