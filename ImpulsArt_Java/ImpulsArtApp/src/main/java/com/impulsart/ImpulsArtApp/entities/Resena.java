package com.impulsart.ImpulsArtApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="Resenas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkCod_Resena")
    private long pkCod_Resena;

    @Column(name = "Comentario", columnDefinition = "TEXT", nullable = false)
    private String comentario;

    @Column(name = "Puntuacion", length = 100, nullable = false)
    private int puntuacion;

}
