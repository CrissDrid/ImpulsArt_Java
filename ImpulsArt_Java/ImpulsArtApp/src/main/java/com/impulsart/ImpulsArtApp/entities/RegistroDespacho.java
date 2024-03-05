package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="registrosdespachos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDespacho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_registro")
    private long id;
    @Column(name = "FechaSalida",length = 50)
    private LocalDate fechaSalida;
    @Column(name = "FechaEntrega",length = 50)
    private LocalDate fechaEntrega;
    @Column(name = "HoraEntrega",length = 10)
    private LocalTime hora;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FkCod_despacho",nullable = false)
    private Despacho despachos;

}
