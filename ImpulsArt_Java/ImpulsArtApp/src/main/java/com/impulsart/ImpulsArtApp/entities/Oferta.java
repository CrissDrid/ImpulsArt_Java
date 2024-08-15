package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Ofertas")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PkCod_oferta", nullable = false)
    private Long PkCod_oferta;

    @Column(name = "Monto", length = 255, nullable = false)
    private int monto;

    @Column(name = "FechaOferta")
    private LocalDate fechaOferta;

    @Column(name = "HoraOferta")
    private LocalTime horaOferta;

    //FOREING KEYS
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Fk_Identificacion", nullable = false)
    private Usuario Usuarios;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "Fk_subasta", nullable = false)
    private Subasta Subastas;

}
