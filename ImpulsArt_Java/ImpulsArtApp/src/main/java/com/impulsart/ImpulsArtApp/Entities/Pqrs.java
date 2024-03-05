package com.impulsart.ImpulsArtApp.Entities;

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
    private long id;
    @Column(name = "Descripcion", length = 200)
    private String Descripcion;
    @Column(name = "Motivo", length = 200)
    private String Motivo;
    @Column(name = "Respuesta", length = 200)
    private String Respuesta;
    @Column(name = "Estado", length = 200)
    private String Estado;
    @Column(name = "FechaPQRS", length = 200)
    private LocalDate fechaPQRS;
    @Column(name = "FechaCierre", length = 200)
    private LocalDate fechaCierre;
}
