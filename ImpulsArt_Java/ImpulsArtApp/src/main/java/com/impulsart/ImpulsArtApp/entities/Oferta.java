package com.impulsart.ImpulsArtApp.entities;

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
    private int Monto;

    @Column(name = "FechaOferta")
    private LocalDate FechaOferta;

    @Column(name = "HoraOferta")
    private LocalTime HoraOferta;

}
