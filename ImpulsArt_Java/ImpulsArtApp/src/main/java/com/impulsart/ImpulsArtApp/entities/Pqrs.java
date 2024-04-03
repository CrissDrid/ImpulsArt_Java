package com.impulsart.ImpulsArtApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="PQRS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pqrs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_PQRS")
    private long pkCod_PQRS;
    @Column(name = "Descripcion", length = 200)
    private String descripcion;
    @Column(name = "Motivo", length = 200)
    private String motivo;
    @Column(name = "Respuesta", length = 200)
    private String respuesta;
    @Column(name = "Estado", length = 200)
    private String estado;
    @Column(name = "FechaPQRS", length = 200)
    private LocalDate fechaPQRS;
    @Column(name = "FechaCierre", length = 200)
    private LocalDate fechaCierre;
}
