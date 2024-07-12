package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="Calificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_Calificacion")
    private long pkCod_Calificacion;

    @Column(name = "Puntaje")
    private int puntaje;

    @Column(name = "Comentarios",length = 255)
    private String comentarios;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "FkCod_Asesor", nullable = false)
    private Asesor asesor;

}
